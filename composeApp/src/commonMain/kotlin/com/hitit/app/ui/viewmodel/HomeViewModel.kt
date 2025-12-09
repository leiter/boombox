package com.hitit.app.ui.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.hitit.app.network.DeezerApiService
import com.hitit.app.service.AudioPlayer
import com.hitit.app.service.MusicService
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

data class HomeUiState(
    val isDeezerAvailable: Boolean? = null,
    val isTestingPlayback: Boolean = false,
    val previewTestStatus: String? = null
)

class HomeViewModel(
    private val musicService: MusicService,
    private val audioPlayer: AudioPlayer,
    private val deezerApi: DeezerApiService
) : ViewModel() {

    private val _uiState = MutableStateFlow(HomeUiState())
    val uiState: StateFlow<HomeUiState> = _uiState.asStateFlow()

    val serviceName: String get() = musicService.serviceName

    init {
        checkDeezerAvailability()
    }

    private fun checkDeezerAvailability() {
        viewModelScope.launch {
            val isAvailable = musicService.isAppInstalled()
            _uiState.value = _uiState.value.copy(isDeezerAvailable = isAvailable)
        }
    }

    fun testDeezerPlayback() {
        viewModelScope.launch {
            _uiState.value = _uiState.value.copy(isTestingPlayback = true)
            // Test with Daft Punk - Get Lucky
            musicService.playTrackById("67238735")
            _uiState.value = _uiState.value.copy(isTestingPlayback = false)
        }
    }

    fun testPreviewPlayback() {
        viewModelScope.launch {
            _uiState.value = _uiState.value.copy(previewTestStatus = "Fetching track info...")

            val trackInfo = deezerApi.getTrackInfo("67238735")

            if (trackInfo?.preview != null) {
                _uiState.value = _uiState.value.copy(previewTestStatus = "Playing: ${trackInfo.title}")
                audioPlayer.play(trackInfo.preview)
            } else {
                _uiState.value = _uiState.value.copy(previewTestStatus = "No preview URL found")
            }
        }
    }

    fun stopPreviewPlayback() {
        audioPlayer.stop()
        _uiState.value = _uiState.value.copy(previewTestStatus = null)
    }
}
