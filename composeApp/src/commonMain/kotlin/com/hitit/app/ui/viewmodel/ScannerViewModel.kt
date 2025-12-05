package com.hitit.app.ui.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.hitit.app.model.QrCodeParser
import com.hitit.app.model.QrCodeResult
import com.hitit.app.model.Track
import com.hitit.app.network.DeezerApiService
import com.hitit.app.repository.HitsterCardRepository
import com.hitit.app.service.AudioPlayer
import com.hitit.app.service.DeviceOrientation
import com.hitit.app.service.DeviceOrientationService
import com.hitit.app.service.MusicService
import kotlinx.coroutines.Job
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

sealed class StatusMessage {
    data object PointCamera : StatusMessage()
    data object Processing : StatusMessage()
    data class ScanError(val error: String) : StatusMessage()
    data class HitsterCard(val cardId: String, val title: String? = null, val artist: String? = null) : StatusMessage()
    data class FetchingTrack(val cardId: String) : StatusMessage()
    data class FlipToPlay(val title: String? = null, val artist: String? = null) : StatusMessage()
    data class NowPlaying(val title: String? = null, val artist: String? = null, val year: Int? = null) : StatusMessage()
    data class OpeningTrack(val serviceName: String, val title: String? = null, val artist: String? = null) : StatusMessage()
    data class Playing(val serviceName: String, val title: String? = null, val artist: String? = null) : StatusMessage()
    data class CouldNotOpen(val serviceName: String) : StatusMessage()
    data class CardNotFound(val cardId: String) : StatusMessage()
    data class SpotifyDetected(val trackId: String) : StatusMessage()
    data class YouTubeDetected(val videoId: String) : StatusMessage()
    data class UrlDetected(val url: String) : StatusMessage()
    data class UnknownQr(val content: String) : StatusMessage()
}

data class ScannerUiState(
    val isProcessing: Boolean = false,
    val status: StatusMessage = StatusMessage.PointCamera,
    val lastScannedCode: String? = null,
    val flashlightOn: Boolean = false,
    val isWaitingForFlip: Boolean = false,
    val isNowPlaying: Boolean = false
)

class ScannerViewModel(
    private val musicService: MusicService,
    private val cardRepository: HitsterCardRepository,
    private val orientationService: DeviceOrientationService,
    private val audioPlayer: AudioPlayer,
    private val deezerApi: DeezerApiService
) : ViewModel() {

    private val _uiState = MutableStateFlow(ScannerUiState())
    val uiState: StateFlow<ScannerUiState> = _uiState.asStateFlow()

    private var pendingTrack: Track? = null
    private var pendingTrackId: String? = null
    private var orientationJob: Job? = null
    private var autoFlipJob: Job? = null

    companion object {
        // Enable this for testing without actually flipping the phone
        const val TEST_MODE_AUTO_FLIP = true
        const val AUTO_FLIP_DELAY_MS = 3000L
    }

    fun toggleFlashlight() {
        _uiState.value = _uiState.value.copy(
            flashlightOn = !_uiState.value.flashlightOn
        )
    }

    fun resetScanner() {
        stopOrientationMonitoring()
        audioPlayer.stop()
        pendingTrack = null
        pendingTrackId = null
        _uiState.value = ScannerUiState(
            flashlightOn = _uiState.value.flashlightOn
        )
    }

    fun startOrientationMonitoring() {
        if (orientationJob != null) return

        orientationJob = viewModelScope.launch {
            orientationService.observeOrientation().collect { orientation ->
                if (orientation == DeviceOrientation.FACE_DOWN && _uiState.value.isWaitingForFlip) {
                    onDeviceFlippedFaceDown()
                }
            }
        }
    }

    fun stopOrientationMonitoring() {
        orientationJob?.cancel()
        orientationJob = null
        autoFlipJob?.cancel()
        autoFlipJob = null
    }

    private fun startAutoFlipTimer() {
        if (!TEST_MODE_AUTO_FLIP) return

        autoFlipJob = viewModelScope.launch {
            delay(AUTO_FLIP_DELAY_MS)
            if (_uiState.value.isWaitingForFlip) {
                onDeviceFlippedFaceDown()
            }
        }
    }

    private fun onDeviceFlippedFaceDown() {
        _uiState.value = _uiState.value.copy(isWaitingForFlip = false)

        // Don't cancel jobs here - we might still be inside one
        // Just stop the orientation monitoring flow
        orientationJob?.cancel()
        orientationJob = null
        autoFlipJob = null // Don't cancel, just clear reference

        // Launch a new coroutine for the async work
        viewModelScope.launch {
            // Show NowPlaying screen with track info and play preview audio in-app
            pendingTrack?.let { track ->
                updateStatus(StatusMessage.NowPlaying(track.title, track.artist, track.year))
                _uiState.value = _uiState.value.copy(isNowPlaying = true)

                // Play 30-second preview in-app (user stays in HitIt)
                val previewUrl = deezerApi.getPreviewUrl(track.id)
                if (previewUrl != null) {
                    audioPlayer.play(previewUrl)
                }
                return@launch
            }

            pendingTrackId?.let { trackId ->
                // Fetch track info from Deezer API
                val trackInfo = deezerApi.getTrackInfo(trackId)
                if (trackInfo != null) {
                    updateStatus(StatusMessage.NowPlaying(
                        trackInfo.title,
                        trackInfo.artist?.name,
                        null // Year not available from Deezer API directly
                    ))
                    _uiState.value = _uiState.value.copy(isNowPlaying = true)

                    // Play 30-second preview in-app (user stays in HitIt)
                    trackInfo.preview?.let { previewUrl ->
                        audioPlayer.play(previewUrl)
                    }
                } else {
                    updateStatus(StatusMessage.NowPlaying(null, null, null))
                    _uiState.value = _uiState.value.copy(isNowPlaying = true)
                }
            }
        }
    }

    override fun onCleared() {
        super.onCleared()
        stopOrientationMonitoring()
        audioPlayer.stop()
    }

    fun onQrCodeScanned(code: String) {
        // Avoid processing the same code twice
        if (_uiState.value.isProcessing || _uiState.value.lastScannedCode == code) {
            return
        }

        _uiState.value = _uiState.value.copy(
            isProcessing = true,
            lastScannedCode = code,
            status = StatusMessage.Processing
        )

        viewModelScope.launch {
            handleScannedCode(code)
        }
    }

    fun onScanError(error: String) {
        _uiState.value = _uiState.value.copy(
            status = StatusMessage.ScanError(error)
        )
    }

    private suspend fun handleScannedCode(code: String) {
        when (val result = QrCodeParser.parse(code)) {
            is QrCodeResult.HitsterCard -> {
                handleHitsterCard(result.cardId)
            }

            is QrCodeResult.DeezerTrack -> {
                // Store pending track and wait for flip
                pendingTrackId = result.trackId
                updateStatus(StatusMessage.FlipToPlay(null, null))
                _uiState.value = _uiState.value.copy(isWaitingForFlip = true, isProcessing = false)
                startOrientationMonitoring()
                startAutoFlipTimer()
                return
            }

            is QrCodeResult.SpotifyTrack -> {
                updateStatus(StatusMessage.SpotifyDetected(result.trackId))
            }

            is QrCodeResult.YouTubeVideo -> {
                updateStatus(StatusMessage.YouTubeDetected(result.videoId))
            }

            is QrCodeResult.GenericUrl -> {
                updateStatus(StatusMessage.UrlDetected(result.url))
            }

            is QrCodeResult.Unknown -> {
                updateStatus(StatusMessage.UnknownQr(result.rawContent))
            }
        }

        _uiState.value = _uiState.value.copy(isProcessing = false)
    }

    private suspend fun handleHitsterCard(cardId: String) {
        // Show fetching status
        updateStatus(StatusMessage.FetchingTrack(cardId))

        // Fetch card data from repository (mock server response)
        val card = cardRepository.getCardById(cardId)

        if (card == null) {
            updateStatus(StatusMessage.CardNotFound(cardId))
            return
        }

        // Get the Deezer track
        val track = card.toTrack()
        if (track != null) {
            // Store pending track and wait for flip
            pendingTrack = track
            updateStatus(StatusMessage.FlipToPlay(track.title, track.artist))
            _uiState.value = _uiState.value.copy(isWaitingForFlip = true, isProcessing = false)
            startOrientationMonitoring()
            startAutoFlipTimer()
        } else {
            updateStatus(StatusMessage.CardNotFound(cardId))
        }
    }

    private fun updateStatus(status: StatusMessage) {
        _uiState.value = _uiState.value.copy(status = status)
    }
}
