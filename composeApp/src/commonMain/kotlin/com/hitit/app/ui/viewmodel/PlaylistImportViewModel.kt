package com.hitit.app.ui.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.hitit.app.model.CardSet
import com.hitit.app.model.CustomCard
import com.hitit.app.network.DeezerApiService
import com.hitit.app.network.DeezerTrackResponse
import com.hitit.app.service.CardSetStore
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

data class PlaylistImportUiState(
    val playlistUrl: String = "",
    val playlistTitle: String? = null,
    val tracks: List<TrackSelection> = emptyList(),
    val isLoading: Boolean = false,
    val error: String? = null,
    val cardSetName: String = "",
    val isSaving: Boolean = false,
    val savedCardSetId: String? = null
)

data class TrackSelection(
    val track: DeezerTrackResponse,
    val isSelected: Boolean = true
)

class PlaylistImportViewModel(
    private val deezerApi: DeezerApiService,
    private val cardSetStore: CardSetStore
) : ViewModel() {
    private val _uiState = MutableStateFlow(PlaylistImportUiState())
    val uiState: StateFlow<PlaylistImportUiState> = _uiState.asStateFlow()

    // Regex patterns for Deezer playlist URLs
    private val playlistIdPattern = Regex("""deezer\.com/(?:\w+/)?playlist/(\d+)""")
    private val directIdPattern = Regex("""^\d+$""")

    fun updatePlaylistUrl(url: String) {
        _uiState.value = _uiState.value.copy(playlistUrl = url)
    }

    fun updateCardSetName(name: String) {
        _uiState.value = _uiState.value.copy(cardSetName = name)
    }

    fun fetchPlaylist() {
        val url = _uiState.value.playlistUrl.trim()
        if (url.isEmpty()) {
            _uiState.value = _uiState.value.copy(error = "Please enter a playlist URL")
            return
        }

        val playlistId = extractPlaylistId(url)
        if (playlistId == null) {
            _uiState.value = _uiState.value.copy(error = "Invalid Deezer playlist URL")
            return
        }

        viewModelScope.launch {
            _uiState.value = _uiState.value.copy(isLoading = true, error = null)
            try {
                val playlist = deezerApi.getPlaylist(playlistId)
                if (playlist != null) {
                    val tracks = playlist.tracks?.data?.map { track ->
                        TrackSelection(track = track, isSelected = true)
                    } ?: emptyList()

                    _uiState.value = _uiState.value.copy(
                        playlistTitle = playlist.title,
                        tracks = tracks,
                        cardSetName = playlist.title ?: "Imported Playlist",
                        isLoading = false
                    )
                } else {
                    _uiState.value = _uiState.value.copy(
                        isLoading = false,
                        error = "Playlist not found"
                    )
                }
            } catch (e: Exception) {
                _uiState.value = _uiState.value.copy(
                    isLoading = false,
                    error = e.message ?: "Failed to load playlist"
                )
            }
        }
    }

    fun toggleTrackSelection(trackId: Long) {
        _uiState.value = _uiState.value.copy(
            tracks = _uiState.value.tracks.map { selection ->
                if (selection.track.id == trackId) {
                    selection.copy(isSelected = !selection.isSelected)
                } else {
                    selection
                }
            }
        )
    }

    fun selectAll() {
        _uiState.value = _uiState.value.copy(
            tracks = _uiState.value.tracks.map { it.copy(isSelected = true) }
        )
    }

    fun deselectAll() {
        _uiState.value = _uiState.value.copy(
            tracks = _uiState.value.tracks.map { it.copy(isSelected = false) }
        )
    }

    fun createCardSet(): String? {
        val state = _uiState.value
        val name = state.cardSetName.trim()
        if (name.isEmpty()) {
            _uiState.value = _uiState.value.copy(error = "Please enter a name for the card set")
            return null
        }

        val selectedTracks = state.tracks.filter { it.isSelected }
        if (selectedTracks.isEmpty()) {
            _uiState.value = _uiState.value.copy(error = "Please select at least one track")
            return null
        }

        _uiState.value = _uiState.value.copy(isSaving = true, error = null)

        val cards = selectedTracks.map { selection ->
            val track = selection.track
            CustomCard(
                deezerId = track.id,
                title = track.title ?: "Unknown",
                artist = track.artist?.name ?: "Unknown",
                year = extractYear(track.releaseDate ?: track.album?.releaseDate),
                albumTitle = track.album?.title,
                albumCoverUrl = track.album?.coverMedium
            )
        }

        val playlistId = extractPlaylistId(state.playlistUrl)
        val cardSet = CardSet(
            name = name,
            description = "Imported from Deezer playlist",
            cards = cards,
            sourcePlaylistId = playlistId
        )

        try {
            cardSetStore.saveCardSet(cardSet)
            _uiState.value = _uiState.value.copy(
                isSaving = false,
                savedCardSetId = cardSet.id
            )
            return cardSet.id
        } catch (e: Exception) {
            _uiState.value = _uiState.value.copy(
                isSaving = false,
                error = e.message ?: "Failed to save card set"
            )
            return null
        }
    }

    private fun extractPlaylistId(url: String): String? {
        // Check if it's already just an ID
        if (directIdPattern.matches(url)) {
            return url
        }
        // Try to extract from URL
        return playlistIdPattern.find(url)?.groupValues?.get(1)
    }

    private fun extractYear(releaseDate: String?): Int? {
        if (releaseDate == null) return null
        // Format: "YYYY-MM-DD"
        return try {
            releaseDate.substring(0, 4).toIntOrNull()
        } catch (e: Exception) {
            null
        }
    }

    val selectedCount: Int
        get() = _uiState.value.tracks.count { it.isSelected }
}
