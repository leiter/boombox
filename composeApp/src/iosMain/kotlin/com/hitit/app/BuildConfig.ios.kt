package com.hitit.app

import kotlin.experimental.ExperimentalNativeApi

actual object AppBuildConfig {
    @OptIn(ExperimentalNativeApi::class)
    actual val isDebug: Boolean = kotlin.native.Platform.isDebugBinary

    /**
     * Set to true to hide debug UI elements even in debug builds.
     * Toggle this flag before creating preview/demo builds.
     */
    actual val isReleasePreview: Boolean = false
}
