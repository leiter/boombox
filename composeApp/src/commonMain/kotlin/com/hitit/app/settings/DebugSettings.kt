package com.hitit.app.settings

import com.hitit.app.AppBuildConfig
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow

data class DebugSettingsState(
    val autoFlipEnabled: Boolean = true,
    val autoFlipDelayMs: Long = 3000L,
    val useDeezerDeeplink: Boolean = true
)

expect class DebugSettingsStore {
    fun getAutoFlipEnabled(): Boolean
    fun setAutoFlipEnabled(enabled: Boolean)
    fun getAutoFlipDelayMs(): Long
    fun setAutoFlipDelayMs(delayMs: Long)
    fun getUseDeezerDeeplink(): Boolean
    fun setUseDeezerDeeplink(enabled: Boolean)
    fun getUseFullVersion(): Boolean
    fun setUseFullVersion(enabled: Boolean)
    fun getFlashEnabled(): Boolean
    fun setFlashEnabled(enabled: Boolean)
}

object DebugSettings {
    private var store: DebugSettingsStore? = null

    private val _state = MutableStateFlow(DebugSettingsState())
    val state: StateFlow<DebugSettingsState> = _state.asStateFlow()

    fun initialize(store: DebugSettingsStore) {
        this.store = store
        // Load persisted values
        _state.value = DebugSettingsState(
            autoFlipEnabled = store.getAutoFlipEnabled(),
            autoFlipDelayMs = store.getAutoFlipDelayMs(),
            useDeezerDeeplink = store.getUseDeezerDeeplink()
        )
    }

    val autoFlipEnabled: Boolean
        get() = if (AppBuildConfig.isReleasePreview) false else _state.value.autoFlipEnabled

    val autoFlipDelayMs: Long
        get() = _state.value.autoFlipDelayMs

    fun setAutoFlipEnabled(enabled: Boolean) {
        store?.setAutoFlipEnabled(enabled)
        _state.value = _state.value.copy(autoFlipEnabled = enabled)
    }

    fun setAutoFlipDelayMs(delayMs: Long) {
        store?.setAutoFlipDelayMs(delayMs)
        _state.value = _state.value.copy(autoFlipDelayMs = delayMs)
    }

    val useDeezerDeeplink: Boolean
        get() = _state.value.useDeezerDeeplink

    fun setUseDeezerDeeplink(enabled: Boolean) {
        store?.setUseDeezerDeeplink(enabled)
        _state.value = _state.value.copy(useDeezerDeeplink = enabled)
    }

    /** Returns true if user prefers full version (Deezer), false for preview */
    fun getUseFullVersion(): Boolean {
        return store?.getUseFullVersion() ?: false
    }

    fun setUseFullVersion(enabled: Boolean) {
        store?.setUseFullVersion(enabled)
    }

    fun getFlashEnabled(): Boolean {
        return store?.getFlashEnabled() ?: false
    }

    fun setFlashEnabled(enabled: Boolean) {
        store?.setFlashEnabled(enabled)
    }
}
