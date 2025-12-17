package com.hitit.app

/**
 * Build configuration using BuildKonfig plugin.
 * Values are compile-time constants, enabling dead code elimination.
 */
object AppBuildConfig {
    val isDebug: Boolean = BuildKonfig.IS_DEBUG
    val isReleasePreview: Boolean = BuildKonfig.IS_RELEASE_PREVIEW
}

/**
 * Returns true if debug options should be shown.
 * Debug options are hidden when:
 * - Running a release build, OR
 * - isReleasePreview is set to true
 */
val AppBuildConfig.showDebugOptions: Boolean
    get() = isDebug && !isReleasePreview
