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
import com.hitit.app.settings.DebugSettings
import com.hitit.app.ui.screens.PlaybackMode
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
    data class NowPlaying(val title: String? = null, val artist: String? = null, val year: Int? = null, val albumCoverUrl: String? = null) : StatusMessage()
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
    val isNowPlaying: Boolean = false,
    val isAudioPlaying: Boolean = false,
    val isUsingExternalPlayback: Boolean = false,
    val isDeezerInstalled: Boolean = false,
    val selectedPlaybackMode: PlaybackMode = PlaybackMode.PREVIEW
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

    init {
        // Load saved preferences (flash always starts off for safety)
        val savedMode = if (DebugSettings.getUseFullVersion()) PlaybackMode.DEEZER else PlaybackMode.PREVIEW
        _uiState.value = _uiState.value.copy(
            selectedPlaybackMode = savedMode,
            flashlightOn = false
        )
        // Reset flash preference to off on app start
        DebugSettings.setFlashEnabled(false)

        // Check if Deezer is installed
        viewModelScope.launch {
            val isDeezerInstalled = musicService.isAppInstalled()
            _uiState.value = _uiState.value.copy(isDeezerInstalled = isDeezerInstalled)
        }
    }

    fun setPlaybackMode(mode: PlaybackMode) {
        _uiState.value = _uiState.value.copy(selectedPlaybackMode = mode)
        // Persist the selection
        DebugSettings.setUseFullVersion(mode == PlaybackMode.DEEZER)
    }

    fun toggleFlashlight() {
        val newFlashState = !_uiState.value.flashlightOn
        _uiState.value = _uiState.value.copy(flashlightOn = newFlashState)
        DebugSettings.setFlashEnabled(newFlashState)
    }

    fun togglePlayPause() {
        if (_uiState.value.isUsingExternalPlayback) {
            // Deezer mode
            if (_uiState.value.isAudioPlaying) {
                // Try to interrupt Deezer by requesting audio focus/session
                // This will cause Deezer to pause on most devices
                audioPlayer.stopExternalPlayback()
                _uiState.value = _uiState.value.copy(isAudioPlaying = false)
            } else {
                // Resume: re-open Deezer with the track
                val trackId = pendingTrack?.id ?: pendingTrackId
                if (trackId != null) {
                    viewModelScope.launch {
                        musicService.playTrackById(trackId)
                    }
                    _uiState.value = _uiState.value.copy(isAudioPlaying = true)
                }
            }
            // Note: We can't pause Deezer from here, audio focus handles that
        } else {
            // Preview mode - control in-app audio
            if (_uiState.value.isAudioPlaying) {
                audioPlayer.pause()
                _uiState.value = _uiState.value.copy(isAudioPlaying = false)
            } else {
                audioPlayer.resume()
                _uiState.value = _uiState.value.copy(isAudioPlaying = true)
            }
        }
    }

    fun onAudioFocusReturned() {
        // When audio focus returns to DukeStar (e.g., user comes back from Deezer),
        // update state to show play icon since external playback has stopped
        if (_uiState.value.isUsingExternalPlayback && _uiState.value.isAudioPlaying) {
            _uiState.value = _uiState.value.copy(isAudioPlaying = false)
        }
    }

    fun resetScanner() {
        stopOrientationMonitoring()
        audioPlayer.stop()
        pendingTrack = null
        pendingTrackId = null
        _uiState.value = ScannerUiState(
            flashlightOn = _uiState.value.flashlightOn,
            isDeezerInstalled = _uiState.value.isDeezerInstalled,
            selectedPlaybackMode = _uiState.value.selectedPlaybackMode
        )
    }

    fun startOrientationMonitoring() {
        if (orientationJob != null) return

        orientationJob = viewModelScope.launch {
            // Small delay to let user see the FlipPhoneScreen before detecting
            kotlinx.coroutines.delay(500)

            orientationService.observeOrientation().collect { orientation ->
                // Trigger when phone is flipped face-down
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
        if (!DebugSettings.autoFlipEnabled) return

        autoFlipJob = viewModelScope.launch {
            delay(DebugSettings.autoFlipDelayMs)
            if (_uiState.value.isWaitingForFlip) {
                onDeviceFlippedFaceDown()
            }
        }
    }

    private fun onDeviceFlippedFaceDown() {
        // Set both flags atomically to prevent flashlight flicker during transition
        _uiState.value = _uiState.value.copy(isWaitingForFlip = false, isNowPlaying = true)

        // Don't cancel jobs here - we might still be inside one
        // Just stop the orientation monitoring flow
        orientationJob?.cancel()
        orientationJob = null
        autoFlipJob = null // Don't cancel, just clear reference

        // Launch a new coroutine for the async work
        viewModelScope.launch {
            // Show NowPlaying screen with track info and play preview audio in-app
            pendingTrack?.let { track ->
                // Fetch full track info from Deezer API to get album cover and preview
                val trackInfo = deezerApi.getTrackInfo(track.id)
                val albumCoverUrl = trackInfo?.album?.coverMedium

                updateStatus(StatusMessage.NowPlaying(
                    track.title,
                    track.artist,
                    track.year,
                    albumCoverUrl
                ))

                // Use selected playback mode
                val useDeeplink = _uiState.value.selectedPlaybackMode == PlaybackMode.DEEZER && _uiState.value.isDeezerInstalled
                if (useDeeplink) {
                    // Open Deezer to play full song
                    musicService.playTrackById(track.id)
                    _uiState.value = _uiState.value.copy(isUsingExternalPlayback = true, isAudioPlaying = true)
                } else {
                    trackInfo?.preview?.let { previewUrl ->
                        audioPlayer.play(previewUrl)
                        _uiState.value = _uiState.value.copy(isAudioPlaying = true, isUsingExternalPlayback = false)
                    }
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
                        null, // Year not available from Deezer API directly
                        trackInfo.album?.coverMedium
                    ))

                    // Use selected playback mode
                    val useDeeplink = _uiState.value.selectedPlaybackMode == PlaybackMode.DEEZER && _uiState.value.isDeezerInstalled
                    if (useDeeplink) {
                        // Open Deezer to play full song
                        musicService.playTrackById(trackId)
                        _uiState.value = _uiState.value.copy(isUsingExternalPlayback = true, isAudioPlaying = true)
                    } else {
                        trackInfo.preview?.let { previewUrl ->
                            audioPlayer.play(previewUrl)
                            _uiState.value = _uiState.value.copy(isAudioPlaying = true, isUsingExternalPlayback = false)
                        }
                    }
                } else {
                    updateStatus(StatusMessage.NowPlaying(null, null, null, null))
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
