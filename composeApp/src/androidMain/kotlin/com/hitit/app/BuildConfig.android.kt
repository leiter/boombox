package com.hitit.app

import android.content.pm.ApplicationInfo

actual object AppBuildConfig {
    // Will be set by MainActivity
    var applicationInfo: ApplicationInfo? = null

    actual val isDebug: Boolean
        get() = applicationInfo?.let {
            (it.flags and ApplicationInfo.FLAG_DEBUGGABLE) != 0
        } ?: true

    /**
     * Set to true to hide debug UI elements even in debug builds.
     * Toggle this flag before creating preview/demo builds.
     */
    actual val isReleasePreview: Boolean = true
}
