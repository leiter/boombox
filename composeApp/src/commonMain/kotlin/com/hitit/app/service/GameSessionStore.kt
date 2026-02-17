package com.hitit.app.service

import com.hitit.app.model.GameSession

/**
 * Platform-specific storage for game sessions.
 * Persists the current game session to allow resuming after app restart.
 */
@Suppress("EXPECT_ACTUAL_CLASSIFIERS_ARE_IN_BETA_WARNING")
expect class GameSessionStore {
    /**
     * Save the current game session
     */
    fun saveSession(session: GameSession)

    /**
     * Load the current game session, or null if none exists
     */
    fun loadCurrentSession(): GameSession?

    /**
     * Clear the current game session
     */
    fun clearSession()
}
