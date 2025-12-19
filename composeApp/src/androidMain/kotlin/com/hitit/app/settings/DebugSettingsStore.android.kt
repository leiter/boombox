package com.hitit.app.settings

import android.content.Context
import android.content.SharedPreferences
import androidx.core.content.edit

@Suppress("EXPECT_ACTUAL_CLASSIFIERS_ARE_IN_BETA_WARNING")
actual class DebugSettingsStore(context: Context) {
    private val prefs: SharedPreferences = context.getSharedPreferences(
        "debug_settings",
        Context.MODE_PRIVATE
    )

    actual fun getAutoFlipEnabled(): Boolean {
        return prefs.getBoolean(KEY_AUTO_FLIP_ENABLED, true)
    }

    actual fun setAutoFlipEnabled(enabled: Boolean) {
        prefs.edit { putBoolean(KEY_AUTO_FLIP_ENABLED, enabled) }
    }

    actual fun getAutoFlipDelayMs(): Long {
        return prefs.getLong(KEY_AUTO_FLIP_DELAY, 3000L)
    }

    actual fun setAutoFlipDelayMs(delayMs: Long) {
        prefs.edit { putLong(KEY_AUTO_FLIP_DELAY, delayMs) }
    }

    actual fun getUseDeezerDeeplink(): Boolean {
        return prefs.getBoolean(KEY_USE_DEEZER_DEEPLINK, true)
    }

    actual fun setUseDeezerDeeplink(enabled: Boolean) {
        prefs.edit { putBoolean(KEY_USE_DEEZER_DEEPLINK, enabled) }
    }

    actual fun getUseFullVersion(): Boolean {
        return prefs.getBoolean(KEY_USE_FULL_VERSION, false)
    }

    actual fun setUseFullVersion(enabled: Boolean) {
        prefs.edit { putBoolean(KEY_USE_FULL_VERSION, enabled) }
    }

    actual fun getFlashEnabled(): Boolean {
        return prefs.getBoolean(KEY_FLASH_ENABLED, false)
    }

    actual fun setFlashEnabled(enabled: Boolean) {
        prefs.edit { putBoolean(KEY_FLASH_ENABLED, enabled) }
    }

    companion object {
        private const val KEY_AUTO_FLIP_ENABLED = "auto_flip_enabled"
        private const val KEY_AUTO_FLIP_DELAY = "auto_flip_delay_ms"
        private const val KEY_USE_DEEZER_DEEPLINK = "use_deezer_deeplink"
        private const val KEY_USE_FULL_VERSION = "use_full_version"
        private const val KEY_FLASH_ENABLED = "flash_enabled"
    }
}
