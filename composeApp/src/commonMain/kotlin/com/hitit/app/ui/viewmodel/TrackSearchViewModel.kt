package com.hitit.app.ui.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.hitit.app.model.CustomCard
import com.hitit.app.network.DeezerApiService
import com.hitit.app.network.DeezerTrackResponse
import kotlinx.coroutines.Job
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

data class TrackSearchUiState(
    val query: String = "",
    val results: List<DeezerTrackResponse> = emptyList(),
    val isSearching: Boolean = false,
    val error: String? = null,
    val selectedTrack: DeezerTrackResponse? = null
)

class TrackSearchViewModel(
    private val deezerApi: DeezerApiService
) : ViewModel() {
    private val _uiState = MutableStateFlow(TrackSearchUiState())
    val uiState: StateFlow<TrackSearchUiState> = _uiState.asStateFlow()

    private var searchJob: Job? = null
    private val debounceDelay = 300L

    fun updateQuery(query: String) {
        _uiState.value = _uiState.value.copy(query = query)

        // Cancel previous search
        searchJob?.cancel()

        if (query.trim().length < 2) {
            _uiState.value = _uiState.value.copy(results = emptyList(), isSearching = false)
            return
        }

        // Debounced search
        searchJob = viewModelScope.launch {
            delay(debounceDelay)
            performSearch(query.trim())
        }
    }

    fun search() {
        val query = _uiState.value.query.trim()
        if (query.length < 2) {
            _uiState.value = _uiState.value.copy(error = "Please enter at least 2 characters")
            return
        }
        searchJob?.cancel()
        viewModelScope.launch {
            performSearch(query)
        }
    }

    private suspend fun performSearch(query: String) {
        _uiState.value = _uiState.value.copy(isSearching = true, error = null)
        try {
            val response = deezerApi.searchTracks(query, limit = 25)
            if (response != null) {
                _uiState.value = _uiState.value.copy(
                    results = response.data,
                    isSearching = false
                )
            } else {
                _uiState.value = _uiState.value.copy(
                    results = emptyList(),
                    isSearching = false,
                    error = "No results found"
                )
            }
        } catch (e: Exception) {
            _uiState.value = _uiState.value.copy(
                isSearching = false,
                error = e.message ?: "Search failed"
            )
        }
    }

    fun selectTrack(track: DeezerTrackResponse) {
        _uiState.value = _uiState.value.copy(selectedTrack = track)
    }

    fun clearSelection() {
        _uiState.value = _uiState.value.copy(selectedTrack = null)
    }

    fun createCardFromSelectedTrack(): CustomCard? {
        val track = _uiState.value.selectedTrack ?: return null
        return CustomCard(
            deezerId = track.id,
            title = track.title ?: "Unknown",
            artist = track.artist?.name ?: "Unknown",
            year = extractYear(track.releaseDate ?: track.album?.releaseDate),
            albumTitle = track.album?.title,
            albumCoverUrl = track.album?.coverMedium
        )
    }

    fun createCardFromTrack(track: DeezerTrackResponse): CustomCard {
        return CustomCard(
            deezerId = track.id,
            title = track.title ?: "Unknown",
            artist = track.artist?.name ?: "Unknown",
            year = extractYear(track.releaseDate ?: track.album?.releaseDate),
            albumTitle = track.album?.title,
            albumCoverUrl = track.album?.coverMedium
        )
    }

    private fun extractYear(releaseDate: String?): Int? {
        if (releaseDate == null) return null
        return try {
            releaseDate.substring(0, 4).toIntOrNull()
        } catch (e: Exception) {
            null
        }
    }

    fun clearError() {
        _uiState.value = _uiState.value.copy(error = null)
    }
}
