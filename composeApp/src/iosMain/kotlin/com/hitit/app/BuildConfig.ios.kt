package com.hitit.app

actual object AppBuildConfig {
    actual val isDebug: Boolean = kotlin.native.Platform.isDebugBinary
}
