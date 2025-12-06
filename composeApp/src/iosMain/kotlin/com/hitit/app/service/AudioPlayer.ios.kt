package com.hitit.app.service

import platform.AVFAudio.AVAudioPlayer
import platform.AVFAudio.AVAudioSession
import platform.AVFAudio.AVAudioSessionCategoryPlayback
import platform.Foundation.NSData
import platform.Foundation.NSURL
import platform.Foundation.dataWithContentsOfURL

actual class AudioPlayer {
    private var audioPlayer: AVAudioPlayer? = null

    actual fun play(url: String) {
        stop() // Stop any existing playback

        try {
            // Set up audio session
            val session = AVAudioSession.sharedInstance()
            session.setCategory(AVAudioSessionCategoryPlayback, error = null)
            session.setActive(true, error = null)

            // Load audio from URL
            val nsUrl = NSURL.URLWithString(url) ?: return
            val data = NSData.dataWithContentsOfURL(nsUrl) ?: return

            audioPlayer = AVAudioPlayer(data, error = null)?.apply {
                numberOfLoops = -1 // Loop indefinitely
                prepareToPlay()
                play()
            }
        } catch (e: Exception) {
            // Handle error silently
        }
    }

    actual fun pause() {
        audioPlayer?.pause()
    }

    actual fun resume() {
        audioPlayer?.play()
    }

    actual fun stop() {
        audioPlayer?.stop()
        audioPlayer = null
    }

    actual fun isPlaying(): Boolean {
        return audioPlayer?.isPlaying() == true
    }

    actual fun stopExternalPlayback() {
        // Request audio session to interrupt other apps (like Deezer)
        try {
            val session = AVAudioSession.sharedInstance()
            session.setCategory(AVAudioSessionCategoryPlayback, error = null)
            session.setActive(true, error = null)
            // Immediately deactivate - we just wanted to interrupt other apps
            session.setActive(false, error = null)
        } catch (e: Exception) {
            // Handle error silently
        }
    }
}
