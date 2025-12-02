package com.hitit.app.service

import com.hitit.app.model.Track

/**
 * Abstraction for music streaming services.
 * Implementations handle platform-specific deep linking to external music apps.
 */
interface MusicService {
    /**
     * The name of the music service (e.g., "Deezer", "Spotify")
     */
    val serviceName: String

    /**
     * Check if the music service app is installed on the device
     */
    suspend fun isAppInstalled(): Boolean

    /**
     * Play a track by opening the music service app
     * @param track The track to play
     * @return true if the app was opened successfully
     */
    suspend fun playTrack(track: Track): Boolean

    /**
     * Play a track by its ID in the music service
     * @param trackId The track ID specific to this music service
     * @return true if the app was opened successfully
     */
    suspend fun playTrackById(trackId: String): Boolean

    /**
     * Get the deep link URL for a track
     * @param trackId The track ID
     * @return The deep link URL
     */
    fun getDeepLinkUrl(trackId: String): String

    /**
     * Get the web fallback URL for a track (if app is not installed)
     * @param trackId The track ID
     * @return The web URL
     */
    fun getWebUrl(trackId: String): String
}
