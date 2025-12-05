package com.hitit.app

import android.content.pm.ApplicationInfo

actual object AppBuildConfig {
    // Will be set by MainActivity
    var applicationInfo: ApplicationInfo? = null

    actual val isDebug: Boolean
        get() = applicationInfo?.let {
            (it.flags and ApplicationInfo.FLAG_DEBUGGABLE) != 0
        } ?: true
}
