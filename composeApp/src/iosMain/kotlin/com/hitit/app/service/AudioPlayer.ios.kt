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
import platform.darwin.dispatch_async
import platform.darwin.dispatch_get_main_queue

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

            // Add observer for completion
            loopObserver = NSNotificationCenter.defaultCenter.addObserverForName(
                name = AVPlayerItemDidPlayToEndTimeNotification,
                `object` = playerItem,
                queue = null
            ) { _ ->
                NSLog("AudioPlayer.ios: Track ended, playback finished")
            }

            // Start playback on main thread
            val p = player
            dispatch_async(dispatch_get_main_queue()) {
                p?.play()
                NSLog("AudioPlayer.ios: Playback started")
            }
        } catch (e: Exception) {
            NSLog("AudioPlayer.ios: Exception in play(): ${e.message ?: "unknown"}")
        }
    }

    actual fun pause() {
        NSLog("AudioPlayer.ios: pause() called")
        val p = player
        if (p == null) {
            NSLog("AudioPlayer.ios: pause() called but player is null")
            return
        }
        dispatch_async(dispatch_get_main_queue()) {
            p.pause()
            NSLog("AudioPlayer.ios: Paused playback")
        }
    }

    actual fun resume() {
        NSLog("AudioPlayer.ios: resume() called")
        val p = player
        if (p == null) {
            NSLog("AudioPlayer.ios: resume() called but player is null")
            return
        }
        dispatch_async(dispatch_get_main_queue()) {
            p.play()
            NSLog("AudioPlayer.ios: Resumed playback")
        }
    }

    actual fun stop() {
        NSLog("AudioPlayer.ios: stop() called")
        // Remove loop observer
        loopObserver?.let {
            NSNotificationCenter.defaultCenter.removeObserver(it)
        }
        loopObserver = null

        val p = player
        player = null
        dispatch_async(dispatch_get_main_queue()) {
            p?.pause()
            p?.replaceCurrentItemWithPlayerItem(null)
            NSLog("AudioPlayer.ios: Stopped playback")
        }
    }

    actual fun isPlaying(): Boolean {
        return player?.timeControlStatus == AVPlayerTimeControlStatusPlaying
    }

    actual fun stopExternalPlayback() {
        NSLog("AudioPlayer.ios: stopExternalPlayback() called")
        // Request audio session to interrupt other apps (like Deezer)
        try {
            val session = AVAudioSession.sharedInstance()
            session.setCategory(AVAudioSessionCategoryPlayback, null)
            memScoped {
                val errorPtr = alloc<ObjCObjectVar<NSError?>>()
                // Activate with notify others option to interrupt other audio
                session.setActive(true, errorPtr.ptr)
                NSLog("AudioPlayer.ios: Audio session activated to interrupt other apps")

                // Keep session active briefly, then deactivate with notify
                // This gives other apps time to receive the interruption
                dispatch_async(dispatch_get_main_queue()) {
                    memScoped {
                        val deactivateErrorPtr = alloc<ObjCObjectVar<NSError?>>()
                        session.setActive(false, deactivateErrorPtr.ptr)
                        NSLog("AudioPlayer.ios: Audio session deactivated")
                    }
                }
            }
        } catch (e: Exception) {
            NSLog("AudioPlayer.ios: stopExternalPlayback error: ${e.message}")
        }
    }
}
