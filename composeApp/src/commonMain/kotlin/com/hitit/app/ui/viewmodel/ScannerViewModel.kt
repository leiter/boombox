package com.hitit.app.ui.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.hitit.app.model.QrCodeParser
import com.hitit.app.model.QrCodeResult
import com.hitit.app.service.MusicService
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

data class ScannerUiState(
    val isProcessing: Boolean = false,
    val statusMessage: String = "Point camera at a Hitster card",
    val lastScannedCode: String? = null,
    val flashlightOn: Boolean = false
)

class ScannerViewModel(
    private val musicService: MusicService
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
            statusMessage = "Processing QR code..."
        )

        viewModelScope.launch {
            handleScannedCode(code)
        }
    }

    fun onScanError(error: String) {
        _uiState.value = _uiState.value.copy(
            statusMessage = "Scan error: $error"
        )
    }

    private suspend fun handleScannedCode(code: String) {
        when (val result = QrCodeParser.parse(code)) {
            is QrCodeResult.HitsterCard -> {
                updateStatus("Hitster card #${result.cardId}\n(Backend lookup not implemented yet)")
            }

            is QrCodeResult.DeezerTrack -> {
                updateStatus("Opening track in ${musicService.serviceName}...")
                val success = musicService.playTrackById(result.trackId)
                if (success) {
                    updateStatus("Playing in ${musicService.serviceName}")
                } else {
                    updateStatus("Could not open ${musicService.serviceName}")
                }
            }

            is QrCodeResult.SpotifyTrack -> {
                updateStatus("Spotify track detected: ${result.trackId}\n(Use Deezer links for this app)")
            }

            is QrCodeResult.YouTubeVideo -> {
                updateStatus("YouTube video detected: ${result.videoId}\n(Use Deezer links for this app)")
            }

            is QrCodeResult.GenericUrl -> {
                updateStatus("URL detected:\n${result.url}")
            }

            is QrCodeResult.Unknown -> {
                updateStatus("Unknown QR code:\n${result.rawContent}")
            }
        }

        _uiState.value = _uiState.value.copy(isProcessing = false)
    }

    private fun updateStatus(message: String) {
        _uiState.value = _uiState.value.copy(statusMessage = message)
    }
}
