package com.hitit.app.model

sealed class QrCodeResult {
    data class HitsterCard(val cardId: String) : QrCodeResult()
    data class DeezerTrack(val trackId: String) : QrCodeResult()
    data class SpotifyTrack(val trackId: String) : QrCodeResult()
    data class YouTubeVideo(val videoId: String) : QrCodeResult()
    data class GenericUrl(val url: String) : QrCodeResult()
    data class Unknown(val rawContent: String) : QrCodeResult()
}

object QrCodeParser {
    private val hitsterPattern = Regex("""hitstergame\.com/\w+/(\d+)""")
    private val deezerTrackPattern = Regex("""deezer\.com/(?:\w+/)?track/(\d+)""")
    private val spotifyTrackPattern = Regex("""spotify\.com/track/([a-zA-Z0-9]+)""")
    private val youtubePattern = Regex("""(?:youtube\.com/watch\?v=|youtu\.be/)([a-zA-Z0-9_-]+)""")

    fun parse(content: String): QrCodeResult {
        hitsterPattern.find(content)?.let {
            return QrCodeResult.HitsterCard(it.groupValues[1])
        }

        deezerTrackPattern.find(content)?.let {
            return QrCodeResult.DeezerTrack(it.groupValues[1])
        }

        spotifyTrackPattern.find(content)?.let {
            return QrCodeResult.SpotifyTrack(it.groupValues[1])
        }

        youtubePattern.find(content)?.let {
            return QrCodeResult.YouTubeVideo(it.groupValues[1])
        }

        if (content.startsWith("http://") || content.startsWith("https://")) {
            return QrCodeResult.GenericUrl(content)
        }

        return QrCodeResult.Unknown(content)
    }
}
