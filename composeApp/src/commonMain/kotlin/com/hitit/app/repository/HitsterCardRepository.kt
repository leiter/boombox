package com.hitit.app.repository

import com.hitit.app.model.HitsterCard

/**
 * Repository for fetching Hitster card data.
 * In production, this would call the actual server.
 */
interface HitsterCardRepository {
    /**
     * Fetch card data by Hitster card ID.
     * @param cardId The numeric ID from the Hitster QR code (e.g., "00001")
     * @return HitsterCard with Deezer track info, or null if not found
     */
    suspend fun getCardById(cardId: String): HitsterCard?
}
