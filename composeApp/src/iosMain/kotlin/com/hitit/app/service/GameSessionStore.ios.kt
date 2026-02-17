package com.hitit.app.service

import com.hitit.app.model.GameSession
import kotlinx.serialization.encodeToString
import kotlinx.serialization.json.Json
import platform.Foundation.NSUserDefaults

@Suppress("EXPECT_ACTUAL_CLASSIFIERS_ARE_IN_BETA_WARNING")
actual class GameSessionStore {
    private val defaults = NSUserDefaults.standardUserDefaults
    private val json = Json { ignoreUnknownKeys = true }

    actual fun saveSession(session: GameSession) {
        val sessionJson = json.encodeToString(session)
        defaults.setObject(sessionJson, KEY_CURRENT_SESSION)
    }

    actual fun loadCurrentSession(): GameSession? {
        val sessionJson = defaults.stringForKey(KEY_CURRENT_SESSION) ?: return null
        return try {
            json.decodeFromString<GameSession>(sessionJson)
        } catch (e: Exception) {
            null
        }
    }

    actual fun clearSession() {
        defaults.removeObjectForKey(KEY_CURRENT_SESSION)
    }

    companion object {
        private const val KEY_CURRENT_SESSION = "current_game_session"
    }
}
