package com.hitit.app.service

import com.hitit.app.model.Track

/**
 * YouTube implementation of MusicService.
 * Opens videos in the YouTube app using deep links.
 * Note: No preview available - opens directly in YouTube app.
 */
class YouTubeMusicService(
    private val appLauncher: AppLauncher
) : MusicService {

    override val serviceName: String = "YouTube"

    override suspend fun isAppInstalled(): Boolean {
        return appLauncher.canOpenUrl(YOUTUBE_SCHEME)
    }

    override suspend fun playTrack(track: Track): Boolean {
        return playTrackById(track.id)
    }

    override suspend fun playTrackById(trackId: String): Boolean {
        // Try deep link first (vnd.youtube for better app targeting)
        val deepLink = getDeepLinkUrl(trackId)
        if (appLauncher.openUrl(deepLink)) {
            return true
        }
        // Fallback to web URL
        return appLauncher.openUrl(getWebUrl(trackId))
    }

    override fun getDeepLinkUrl(trackId: String): String {
        return "$YOUTUBE_SCHEME$trackId"
    }

    override fun getWebUrl(trackId: String): String {
        return "$YOUTUBE_WEB_BASE?v=$trackId"
    }

    companion object {
        private const val YOUTUBE_SCHEME = "vnd.youtube://"
        private const val YOUTUBE_WEB_BASE = "https://www.youtube.com/watch"
    }
}
