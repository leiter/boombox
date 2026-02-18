package com.hitit.app.model

import com.benasher44.uuid.uuid4
import kotlinx.datetime.Clock
import kotlinx.serialization.Serializable

/**
 * A named collection of custom cards that can be used for gameplay.
 * Created from Deezer playlists or by manually adding tracks.
 */
@Serializable
data class CardSet(
    val id: String = uuid4().toString(),
    val name: String,
    val description: String? = null,
    val cards: List<CustomCard> = emptyList(),
    val sourcePlaylistId: String? = null,
    val createdAt: Long = Clock.System.now().toEpochMilliseconds(),
    val updatedAt: Long = Clock.System.now().toEpochMilliseconds()
) {
    val cardCount: Int get() = cards.size

    /**
     * Add a card to this set
     */
    fun addCard(card: CustomCard): CardSet {
        return copy(
            cards = cards + card,
            updatedAt = Clock.System.now().toEpochMilliseconds()
        )
    }

    /**
     * Remove a card from this set by ID
     */
    fun removeCard(cardId: String): CardSet {
        return copy(
            cards = cards.filter { it.id != cardId },
            updatedAt = Clock.System.now().toEpochMilliseconds()
        )
    }

    /**
     * Update a card in this set
     */
    fun updateCard(card: CustomCard): CardSet {
        return copy(
            cards = cards.map { if (it.id == card.id) card else it },
            updatedAt = Clock.System.now().toEpochMilliseconds()
        )
    }

    /**
     * Reorder cards by moving a card from one index to another
     */
    fun moveCard(fromIndex: Int, toIndex: Int): CardSet {
        if (fromIndex == toIndex || fromIndex < 0 || toIndex < 0 ||
            fromIndex >= cards.size || toIndex >= cards.size
        ) {
            return this
        }
        val mutableCards = cards.toMutableList()
        val card = mutableCards.removeAt(fromIndex)
        mutableCards.add(toIndex, card)
        return copy(
            cards = mutableCards,
            updatedAt = Clock.System.now().toEpochMilliseconds()
        )
    }

    /**
     * Update the name and description of this set
     */
    fun updateInfo(name: String, description: String?): CardSet {
        return copy(
            name = name,
            description = description,
            updatedAt = Clock.System.now().toEpochMilliseconds()
        )
    }
}
