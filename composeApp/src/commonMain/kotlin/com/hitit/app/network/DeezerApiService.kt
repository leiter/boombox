package com.hitit.app.network

import io.ktor.client.*
import io.ktor.client.call.*
import io.ktor.client.plugins.contentnegotiation.*
import io.ktor.client.request.*
import io.ktor.serialization.kotlinx.json.*
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable
import kotlinx.serialization.json.Json

@Serializable
data class DeezerTrackResponse(
    val id: Long,
    val title: String? = null,
    val preview: String? = null, // 30-second preview URL
    val artist: DeezerArtist? = null,
    val album: DeezerAlbum? = null,
    @SerialName("release_date")
    val releaseDate: String? = null // Format: "YYYY-MM-DD"
)

@Serializable
data class DeezerArtist(
    val id: Long,
    val name: String? = null
)

@Serializable
data class DeezerAlbum(
    val id: Long,
    val title: String? = null,
    @SerialName("cover_medium")
    val coverMedium: String? = null,
    @SerialName("release_date")
    val releaseDate: String? = null // Format: "YYYY-MM-DD"
)

@Serializable
data class DeezerPlaylistResponse(
    val id: Long,
    val title: String? = null,
    val description: String? = null,
    @SerialName("nb_tracks")
    val trackCount: Int? = null,
    val tracks: DeezerTracksData? = null
)

@Serializable
data class DeezerTracksData(
    val data: List<DeezerTrackResponse> = emptyList()
)

@Serializable
data class DeezerSearchResponse(
    val data: List<DeezerTrackResponse> = emptyList(),
    val total: Int? = null,
    val next: String? = null
)

class DeezerApiService {
    private val httpClient = HttpClient {
        install(ContentNegotiation) {
            json(Json {
                prettyPrint = true
                isLenient = true
                ignoreUnknownKeys = true
            })
        }
    }

    /**
     * Fetch track info from Deezer API including preview URL
     */
    suspend fun getTrackInfo(trackId: String): DeezerTrackResponse? {
        return try {
            println("DeezerApiService: Fetching track info for ID: $trackId")
            val response: DeezerTrackResponse = httpClient.get("https://api.deezer.com/track/$trackId").body()
            println("DeezerApiService: Got response - title: ${response.title}, preview: ${response.preview}")
            response
        } catch (e: Exception) {
            println("DeezerApiService: Error fetching track: ${e.message}")
            null
        }
    }

    /**
     * Get just the preview URL for a track
     */
    suspend fun getPreviewUrl(trackId: String): String? {
        val url = getTrackInfo(trackId)?.preview
        println("DeezerApiService: Preview URL for $trackId: $url")
        return url
    }

    /**
     * Fetch playlist info and tracks from Deezer API
     * @param playlistId The Deezer playlist ID
     * @return Playlist with tracks, or null if not found
     */
    suspend fun getPlaylist(playlistId: String): DeezerPlaylistResponse? {
        return try {
            println("DeezerApiService: Fetching playlist: $playlistId")
            val response: DeezerPlaylistResponse = httpClient.get("https://api.deezer.com/playlist/$playlistId").body()
            println("DeezerApiService: Got playlist - title: ${response.title}, tracks: ${response.tracks?.data?.size}")
            response
        } catch (e: Exception) {
            println("DeezerApiService: Error fetching playlist: ${e.message}")
            null
        }
    }

    /**
     * Search for tracks on Deezer
     * @param query The search query
     * @param limit Maximum number of results (default 25)
     * @return Search results, or null if error
     */
    suspend fun searchTracks(query: String, limit: Int = 25): DeezerSearchResponse? {
        return try {
            println("DeezerApiService: Searching tracks: $query")
            val response: DeezerSearchResponse = httpClient.get("https://api.deezer.com/search/track") {
                parameter("q", query)
                parameter("limit", limit)
            }.body()
            println("DeezerApiService: Got ${response.data.size} search results")
            response
        } catch (e: Exception) {
            println("DeezerApiService: Error searching tracks: ${e.message}")
            null
        }
    }

    fun close() {
        httpClient.close()
    }
}
