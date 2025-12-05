package com.hitit.app.service

import android.media.AudioAttributes
import android.media.MediaPlayer
import android.util.Log

actual class AudioPlayer {
    private var mediaPlayer: MediaPlayer? = null

    actual fun play(url: String) {
        Log.d("AudioPlayer", "play() called with URL: $url")
        stop() // Stop any existing playback

        try {
            mediaPlayer = MediaPlayer().apply {
                setAudioAttributes(
                    AudioAttributes.Builder()
                        .setContentType(AudioAttributes.CONTENT_TYPE_MUSIC)
                        .setUsage(AudioAttributes.USAGE_MEDIA)
                        .build()
                )

                // Set listeners BEFORE prepareAsync
                setOnPreparedListener { mp ->
                    Log.d("AudioPlayer", "onPrepared - starting playback")
                    mp.start()
                }

                setOnErrorListener { _, what, extra ->
                    Log.e("AudioPlayer", "MediaPlayer error: what=$what, extra=$extra")
                    false
                }

                setOnCompletionListener {
                    Log.d("AudioPlayer", "onCompletion - looping")
                    it.seekTo(0)
                    it.start()
                }

                Log.d("AudioPlayer", "Setting data source...")
                setDataSource(url)
                Log.d("AudioPlayer", "Calling prepareAsync...")
                prepareAsync()
            }
        } catch (e: Exception) {
            Log.e("AudioPlayer", "Exception in play(): ${e.message}", e)
        }
    }

    actual fun pause() {
        mediaPlayer?.let {
            if (it.isPlaying) {
                it.pause()
            }
        }
    }

    actual fun resume() {
        mediaPlayer?.let {
            if (!it.isPlaying) {
                it.start()
            }
        }
    }

    actual fun stop() {
        mediaPlayer?.let {
            if (it.isPlaying) {
                it.stop()
            }
            it.release()
        }
        mediaPlayer = null
    }

    actual fun isPlaying(): Boolean {
        return mediaPlayer?.isPlaying == true
    }
}
