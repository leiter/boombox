package com.hitit.app.service

import platform.Foundation.NSURL
import platform.UIKit.UIApplication

actual class AppLauncher {

    actual fun openUrl(url: String): Boolean {
        val nsUrl = NSURL.URLWithString(url) ?: return false
        return UIApplication.sharedApplication.openURL(nsUrl)
    }

    actual fun canOpenUrl(urlScheme: String): Boolean {
        val nsUrl = NSURL.URLWithString(urlScheme) ?: return false
        return UIApplication.sharedApplication.canOpenURL(nsUrl)
    }
}
