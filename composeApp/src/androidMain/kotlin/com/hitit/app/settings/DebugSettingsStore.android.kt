package com.hitit.app.settings

import android.content.Context
import android.content.SharedPreferences

actual class DebugSettingsStore(context: Context) {
    private val prefs: SharedPreferences = context.getSharedPreferences(
        "debug_settings",
        Context.MODE_PRIVATE
    )

    actual fun getAutoFlipEnabled(): Boolean {
        return prefs.getBoolean(KEY_AUTO_FLIP_ENABLED, true)
    }

    actual fun setAutoFlipEnabled(enabled: Boolean) {
        prefs.edit().putBoolean(KEY_AUTO_FLIP_ENABLED, enabled).apply()
    }

    actual fun getAutoFlipDelayMs(): Long {
        return prefs.getLong(KEY_AUTO_FLIP_DELAY, 3000L)
    }

    actual fun setAutoFlipDelayMs(delayMs: Long) {
        prefs.edit().putLong(KEY_AUTO_FLIP_DELAY, delayMs).apply()
    }

    actual fun getUseDeezerDeeplink(): Boolean {
        return prefs.getBoolean(KEY_USE_DEEZER_DEEPLINK, true)
    }

    actual fun setUseDeezerDeeplink(enabled: Boolean) {
        prefs.edit().putBoolean(KEY_USE_DEEZER_DEEPLINK, enabled).apply()
    }

    companion object {
        private const val KEY_AUTO_FLIP_ENABLED = "auto_flip_enabled"
        private const val KEY_AUTO_FLIP_DELAY = "auto_flip_delay_ms"
        private const val KEY_USE_DEEZER_DEEPLINK = "use_deezer_deeplink"
    }
}
