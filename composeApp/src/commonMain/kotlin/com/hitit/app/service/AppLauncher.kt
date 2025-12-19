package com.hitit.app.service

/**
 * Platform-specific interface for launching external apps via deep links
 */
@Suppress("EXPECT_ACTUAL_CLASSIFIERS_ARE_IN_BETA_WARNING")
expect class AppLauncher {
    /**
     * Open a URL or deep link
     * @param url The URL or deep link to open
     * @return true if successfully opened
     */
    fun openUrl(url: String): Boolean

    /**
     * Check if an app can handle a given URL scheme
     * @param urlScheme The URL scheme (e.g., "deezer://")
     * @return true if an app can handle the scheme
     */
    fun canOpenUrl(urlScheme: String): Boolean
}
