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
    val album: DeezerAlbum? = null
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
    val coverMedium: String? = null
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

    fun close() {
        httpClient.close()
    }
}
