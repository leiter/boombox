package com.hitit.app.service

import com.hitit.app.model.Track

/**
 * Deezer implementation of MusicService.
 * Opens tracks in the Deezer app using deep links.
 */
class DeezerMusicService(
    private val appLauncher: AppLauncher
) : MusicService {

    override val serviceName: String = "Deezer"

    override suspend fun isAppInstalled(): Boolean {
        return appLauncher.canOpenUrl(DEEZER_SCHEME)
    }

    override suspend fun playTrack(track: Track): Boolean {
        return playTrackById(track.id)
    }

    override suspend fun playTrackById(trackId: String): Boolean {
        val deepLink = getDeepLinkUrl(trackId)

        // Try deep link first
        if (appLauncher.openUrl(deepLink)) {
            return true
        }

        // Fall back to web URL
        val webUrl = getWebUrl(trackId)
        return appLauncher.openUrl(webUrl)
    }

    override fun getDeepLinkUrl(trackId: String): String {
        return "$DEEZER_SCHEME/track/$trackId"
    }

    override fun getWebUrl(trackId: String): String {
        return "$DEEZER_WEB_BASE/track/$trackId"
    }

    companion object {
        private const val DEEZER_SCHEME = "deezer://"
        private const val DEEZER_WEB_BASE = "https://www.deezer.com"
    }
}
