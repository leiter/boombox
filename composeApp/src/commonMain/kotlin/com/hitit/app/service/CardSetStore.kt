package com.hitit.app.service

import com.hitit.app.model.CardSet

/**
 * Platform-specific storage for card sets.
 * Persists custom card sets created by the user.
 */
@Suppress("EXPECT_ACTUAL_CLASSIFIERS_ARE_IN_BETA_WARNING")
expect class CardSetStore {
    /**
     * Save a card set (creates new or updates existing)
     */
    fun saveCardSet(cardSet: CardSet)

    /**
     * Load all saved card sets
     */
    fun loadAllCardSets(): List<CardSet>

    /**
     * Load a specific card set by ID
     */
    fun loadCardSet(cardSetId: String): CardSet?

    /**
     * Delete a card set by ID
     */
    fun deleteCardSet(cardSetId: String)

    /**
     * Clear all card sets
     */
    fun clearAll()
}
