package com.hitit.app

import kotlin.experimental.ExperimentalNativeApi

actual object AppBuildConfig {
    @OptIn(ExperimentalNativeApi::class)
    actual val isDebug: Boolean = kotlin.native.Platform.isDebugBinary
}
