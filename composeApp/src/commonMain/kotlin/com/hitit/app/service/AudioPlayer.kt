package com.hitit.app.service

/**
 * Platform-specific audio player for playing track previews
 */
@Suppress("EXPECT_ACTUAL_CLASSIFIERS_ARE_IN_BETA_WARNING")
expect class AudioPlayer {
    /**
     * Play audio from a URL
     */
    fun play(url: String)

    /**
     * Pause playback
     */
    fun pause()

    /**
     * Resume playback
     */
    fun resume()

    /**
     * Stop playback and release resources
     */
    fun stop()

    /**
     * Check if currently playing
     */
    fun isPlaying(): Boolean

    /**
     * Request audio focus to stop external apps (like Deezer) from playing
     */
    fun stopExternalPlayback()
}
