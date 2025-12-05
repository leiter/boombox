package com.hitit.app.settings

import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow

data class DebugSettingsState(
    val autoFlipEnabled: Boolean = true,
    val autoFlipDelayMs: Long = 3000L
)

expect class DebugSettingsStore {
    fun getAutoFlipEnabled(): Boolean
    fun setAutoFlipEnabled(enabled: Boolean)
    fun getAutoFlipDelayMs(): Long
    fun setAutoFlipDelayMs(delayMs: Long)
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
            autoFlipDelayMs = store.getAutoFlipDelayMs()
        )
    }

    val autoFlipEnabled: Boolean
        get() = _state.value.autoFlipEnabled

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
}
