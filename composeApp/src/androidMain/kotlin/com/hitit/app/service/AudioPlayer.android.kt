package com.hitit.app.service

import android.content.Context
import android.media.AudioAttributes
import android.media.AudioFocusRequest
import android.media.AudioManager
import android.media.MediaPlayer
import android.os.Build
import android.util.Log

@Suppress("EXPECT_ACTUAL_CLASSIFIERS_ARE_IN_BETA_WARNING")
actual class AudioPlayer(context: Context) {
    private var mediaPlayer: MediaPlayer? = null
    private val audioManager = context.getSystemService(Context.AUDIO_SERVICE) as AudioManager
    private var audioFocusRequest: AudioFocusRequest? = null

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
                    Log.d("AudioPlayer", "onCompletion - playback finished")
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

    actual fun stopExternalPlayback() {
        Log.d("AudioPlayer", "stopExternalPlayback() - requesting audio focus to stop other apps")

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            val attrs = AudioAttributes.Builder()
                .setUsage(AudioAttributes.USAGE_MEDIA)
                .setContentType(AudioAttributes.CONTENT_TYPE_MUSIC)
                .build()

            audioFocusRequest = AudioFocusRequest.Builder(AudioManager.AUDIOFOCUS_GAIN)
                .setAudioAttributes(attrs)
                .setOnAudioFocusChangeListener { }
                .build()

            audioFocusRequest?.let { request ->
                audioManager.requestAudioFocus(request)
                // Immediately abandon focus - we just wanted to interrupt other apps
                audioManager.abandonAudioFocusRequest(request)
            }
        } else {
            @Suppress("DEPRECATION")
            audioManager.requestAudioFocus(
                { },
                AudioManager.STREAM_MUSIC,
                AudioManager.AUDIOFOCUS_GAIN
            )
            @Suppress("DEPRECATION")
            audioManager.abandonAudioFocus { }
        }
    }
}
