package com.hitit.app

expect object AppBuildConfig {
    val isDebug: Boolean

    /**
     * Set to true in code to hide debug UI elements even in debug builds.
     * Useful for creating preview/demo builds for stakeholders.
     */
    val isReleasePreview: Boolean
}

/**
 * Returns true if debug options should be shown.
 * Debug options are hidden when:
 * - Running a release build, OR
 * - isReleasePreview is set to true
 */
val AppBuildConfig.showDebugOptions: Boolean
    get() = isDebug && !isReleasePreview
