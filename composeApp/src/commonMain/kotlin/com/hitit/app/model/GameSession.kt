package com.hitit.app.model

import com.benasher44.uuid.uuid4
import kotlinx.datetime.Clock
import kotlinx.serialization.Serializable

enum class CardResult {
    CORRECT,
    INCORRECT,
    SKIPPED
}

@Serializable
data class PlayedCard(
    val cardId: String,
    val trackId: String,
    val title: String?,
    val artist: String?,
    val year: Int?,
    val result: CardResult,
    val playedAt: Long = Clock.System.now().toEpochMilliseconds()
)

@Serializable
data class GameSession(
    val id: String = uuid4().toString(),
    val startedAt: Long = Clock.System.now().toEpochMilliseconds(),
    val cards: List<PlayedCard> = emptyList()
) {
    val score: Int get() = cards.count { it.result == CardResult.CORRECT }
    val total: Int get() = cards.size

    fun addCard(card: PlayedCard): GameSession {
        return copy(cards = cards + card)
    }
}
