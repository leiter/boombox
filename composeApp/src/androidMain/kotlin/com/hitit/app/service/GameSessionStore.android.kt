package com.hitit.app.service

import android.content.Context
import android.content.SharedPreferences
import androidx.core.content.edit
import com.hitit.app.model.GameSession
import kotlinx.serialization.encodeToString
import kotlinx.serialization.json.Json

@Suppress("EXPECT_ACTUAL_CLASSIFIERS_ARE_IN_BETA_WARNING")
actual class GameSessionStore(context: Context) {
    private val prefs: SharedPreferences = context.getSharedPreferences(
        PREFS_NAME,
        Context.MODE_PRIVATE
    )
    private val json = Json { ignoreUnknownKeys = true }

    actual fun saveSession(session: GameSession) {
        val sessionJson = json.encodeToString(session)
        prefs.edit { putString(KEY_CURRENT_SESSION, sessionJson) }
    }

    actual fun loadCurrentSession(): GameSession? {
        val sessionJson = prefs.getString(KEY_CURRENT_SESSION, null) ?: return null
        return try {
            json.decodeFromString<GameSession>(sessionJson)
        } catch (e: Exception) {
            null
        }
    }

    actual fun clearSession() {
        prefs.edit { remove(KEY_CURRENT_SESSION) }
    }

    companion object {
        private const val PREFS_NAME = "game_session"
        private const val KEY_CURRENT_SESSION = "current_session"
    }
}
