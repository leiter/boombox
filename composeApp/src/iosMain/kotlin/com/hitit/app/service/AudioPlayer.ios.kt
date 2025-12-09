package com.hitit.app.service

import kotlinx.cinterop.BetaInteropApi
import kotlinx.cinterop.ExperimentalForeignApi
import kotlinx.cinterop.ObjCObjectVar
import kotlinx.cinterop.alloc
import kotlinx.cinterop.memScoped
import kotlinx.cinterop.ptr
import platform.AVFAudio.AVAudioSession
import platform.AVFAudio.AVAudioSessionCategoryPlayback
import platform.AVFAudio.setActive
import platform.AVFoundation.AVPlayer
import platform.AVFoundation.AVPlayerItem
import platform.AVFoundation.AVPlayerItemDidPlayToEndTimeNotification
import platform.AVFoundation.AVPlayerTimeControlStatusPlaying
import platform.AVFoundation.currentItem
import platform.AVFoundation.pause
import platform.AVFoundation.play
import platform.AVFoundation.replaceCurrentItemWithPlayerItem
import platform.AVFoundation.seekToTime
import platform.AVFoundation.timeControlStatus
import platform.CoreMedia.CMTimeMake
import platform.Foundation.NSError
import platform.Foundation.NSLog
import platform.Foundation.NSNotificationCenter
import platform.Foundation.NSURL
import platform.darwin.NSObjectProtocol

@OptIn(ExperimentalForeignApi::class, BetaInteropApi::class)
actual class AudioPlayer {
    private var player: AVPlayer? = null
    private var loopObserver: NSObjectProtocol? = null

    actual fun play(url: String) {
        NSLog("AudioPlayer.ios: play() called with URL: $url")
        stop() // Stop any existing playback

        try {
            // Set up audio session
            val session = AVAudioSession.sharedInstance()
            session.setCategory(AVAudioSessionCategoryPlayback, null)
            memScoped {
                val errorPtr = alloc<ObjCObjectVar<NSError?>>()
                session.setActive(true, errorPtr.ptr)
            }
            NSLog("AudioPlayer.ios: Audio session configured")

            // Create URL and player item
            val nsUrl = NSURL.URLWithString(url)
            if (nsUrl == null) {
                NSLog("AudioPlayer.ios: Failed to create NSURL from: $url")
                return
            }

            val playerItem = AVPlayerItem(uRL = nsUrl)
            player = AVPlayer(playerItem = playerItem)

            // Add observer for looping
            loopObserver = NSNotificationCenter.defaultCenter.addObserverForName(
                name = AVPlayerItemDidPlayToEndTimeNotification,
                `object` = playerItem,
                queue = null
            ) { _ ->
                NSLog("AudioPlayer.ios: Track ended, looping...")
                player?.seekToTime(CMTimeMake(value = 0, timescale = 1))
                player?.play()
            }

            // Start playback
            player?.play()
            NSLog("AudioPlayer.ios: Playback started")
        } catch (e: Exception) {
            NSLog("AudioPlayer.ios: Exception in play(): ${e.message ?: "unknown"}")
        }
    }

    actual fun pause() {
        player?.pause()
    }

    actual fun resume() {
        player?.play()
    }

    actual fun stop() {
        // Remove loop observer
        loopObserver?.let {
            NSNotificationCenter.defaultCenter.removeObserver(it)
        }
        loopObserver = null

        player?.pause()
        player?.replaceCurrentItemWithPlayerItem(null)
        player = null
    }

    actual fun isPlaying(): Boolean {
        return player?.timeControlStatus == AVPlayerTimeControlStatusPlaying
    }

    actual fun stopExternalPlayback() {
        // Request audio session to interrupt other apps (like Deezer)
        try {
            val session = AVAudioSession.sharedInstance()
            session.setCategory(AVAudioSessionCategoryPlayback, null)
            memScoped {
                val errorPtr = alloc<ObjCObjectVar<NSError?>>()
                session.setActive(true, errorPtr.ptr)
                // Immediately deactivate - we just wanted to interrupt other apps
                session.setActive(false, errorPtr.ptr)
            }
        } catch (e: Exception) {
            // Handle error silently
        }
    }
}
