package com.hitit.app.settings

import platform.Foundation.NSUserDefaults

actual class DebugSettingsStore {
    private val defaults = NSUserDefaults.standardUserDefaults

    actual fun getAutoFlipEnabled(): Boolean {
        return if (defaults.objectForKey(KEY_AUTO_FLIP_ENABLED) != null) {
            defaults.boolForKey(KEY_AUTO_FLIP_ENABLED)
        } else {
            true // default value
        }
    }

    actual fun setAutoFlipEnabled(enabled: Boolean) {
        defaults.setBool(enabled, KEY_AUTO_FLIP_ENABLED)
    }

    actual fun getAutoFlipDelayMs(): Long {
        return if (defaults.objectForKey(KEY_AUTO_FLIP_DELAY) != null) {
            defaults.integerForKey(KEY_AUTO_FLIP_DELAY)
        } else {
            3000L // default value
        }
    }

    actual fun setAutoFlipDelayMs(delayMs: Long) {
        defaults.setInteger(delayMs, KEY_AUTO_FLIP_DELAY)
    }

    actual fun getUseDeezerDeeplink(): Boolean {
        return if (defaults.objectForKey(KEY_USE_DEEZER_DEEPLINK) != null) {
            defaults.boolForKey(KEY_USE_DEEZER_DEEPLINK)
        } else {
            true // default value
        }
    }

    actual fun setUseDeezerDeeplink(enabled: Boolean) {
        defaults.setBool(enabled, KEY_USE_DEEZER_DEEPLINK)
    }

    actual fun getUseFullVersion(): Boolean {
        return if (defaults.objectForKey(KEY_USE_FULL_VERSION) != null) {
            defaults.boolForKey(KEY_USE_FULL_VERSION)
        } else {
            false // default value (preview)
        }
    }

    actual fun setUseFullVersion(enabled: Boolean) {
        defaults.setBool(enabled, KEY_USE_FULL_VERSION)
    }

    companion object {
        private const val KEY_AUTO_FLIP_ENABLED = "auto_flip_enabled"
        private const val KEY_AUTO_FLIP_DELAY = "auto_flip_delay_ms"
        private const val KEY_USE_DEEZER_DEEPLINK = "use_deezer_deeplink"
        private const val KEY_USE_FULL_VERSION = "use_full_version"
    }
}
