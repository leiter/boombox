package com.hitit.app.service

import platform.Foundation.NSLog
import platform.Foundation.NSURL
import platform.UIKit.UIApplication

actual class AppLauncher {

    actual fun openUrl(url: String): Boolean {
        val nsUrl = NSURL.URLWithString(url)
        if (nsUrl == null) {
            NSLog("AppLauncher.ios: Failed to create NSURL from: $url")
            return false
        }

        // Check if we can open the URL first
        if (!UIApplication.sharedApplication.canOpenURL(nsUrl)) {
            NSLog("AppLauncher.ios: Cannot open URL: $url")
            return false
        }

        // Use modern async API (openURL:options:completionHandler:)
        NSLog("AppLauncher.ios: Opening URL: $url")
        UIApplication.sharedApplication.openURL(
            nsUrl,
            options = emptyMap<Any?, Any?>(),
            completionHandler = { success ->
                NSLog("AppLauncher.ios: openURL completed with success: $success")
            }
        )
        return true
    }

    actual fun canOpenUrl(urlScheme: String): Boolean {
        val nsUrl = NSURL.URLWithString(urlScheme) ?: return false
        val canOpen = UIApplication.sharedApplication.canOpenURL(nsUrl)
        NSLog("AppLauncher.ios: canOpenURL($urlScheme) = $canOpen")
        return canOpen
    }
}
