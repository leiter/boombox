package com.hitit.app.service

/**
 * Platform-specific audio player for playing track previews
 */
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
}
