package com.hitit.app.service

import com.hitit.app.model.Track

/**
 * Spotify implementation of MusicService.
 * Opens tracks in the Spotify app using deep links.
 * Note: No 30-second preview available (requires OAuth).
 */
class SpotifyMusicService(
    private val appLauncher: AppLauncher
) : MusicService {

    override val serviceName: String = "Spotify"

    override suspend fun isAppInstalled(): Boolean {
        return appLauncher.canOpenUrl(SPOTIFY_SCHEME)
    }

    override suspend fun playTrack(track: Track): Boolean {
        return playTrackById(track.id)
    }

    override suspend fun playTrackById(trackId: String): Boolean {
        // Try deep link first, fallback to web
        val deepLink = getDeepLinkUrl(trackId)
        if (appLauncher.openUrl(deepLink)) {
            return true
        }
        // Fallback to web URL
        return appLauncher.openUrl(getWebUrl(trackId))
    }

    override fun getDeepLinkUrl(trackId: String): String {
        return "$SPOTIFY_SCHEME/track/$trackId"
    }

    override fun getWebUrl(trackId: String): String {
        return "$SPOTIFY_WEB_BASE/track/$trackId"
    }

    companion object {
        private const val SPOTIFY_SCHEME = "spotify://"
        private const val SPOTIFY_WEB_BASE = "https://open.spotify.com"
    }
}
