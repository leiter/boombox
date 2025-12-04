package com.hitit.app.ui.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.hitit.app.model.QrCodeParser
import com.hitit.app.model.QrCodeResult
import com.hitit.app.repository.HitsterCardRepository
import com.hitit.app.service.MusicService
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
    val flashlightOn: Boolean = false
)

class ScannerViewModel(
    private val musicService: MusicService,
    private val cardRepository: HitsterCardRepository
) : ViewModel() {

    private val _uiState = MutableStateFlow(ScannerUiState())
    val uiState: StateFlow<ScannerUiState> = _uiState.asStateFlow()

    fun toggleFlashlight() {
        _uiState.value = _uiState.value.copy(
            flashlightOn = !_uiState.value.flashlightOn
        )
    }

    fun resetScanner() {
        _uiState.value = ScannerUiState(
            flashlightOn = _uiState.value.flashlightOn
        )
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
                updateStatus(StatusMessage.OpeningTrack(musicService.serviceName))
                val success = musicService.playTrackById(result.trackId)
                if (success) {
                    updateStatus(StatusMessage.Playing(musicService.serviceName))
                } else {
                    updateStatus(StatusMessage.CouldNotOpen(musicService.serviceName))
                }
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

        // Show card info
        updateStatus(StatusMessage.HitsterCard(cardId, card.deezerTitle ?: card.title, card.deezerArtist ?: card.artist))

        // Get the Deezer track and play it
        val track = card.toTrack()
        if (track != null) {
            updateStatus(StatusMessage.OpeningTrack(musicService.serviceName, track.title, track.artist))
            val success = musicService.playTrack(track)
            if (success) {
                updateStatus(StatusMessage.Playing(musicService.serviceName, track.title, track.artist))
            } else {
                updateStatus(StatusMessage.CouldNotOpen(musicService.serviceName))
            }
        } else {
            updateStatus(StatusMessage.CardNotFound(cardId))
        }
    }

    private fun updateStatus(status: StatusMessage) {
        _uiState.value = _uiState.value.copy(status = status)
    }
}
