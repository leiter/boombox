package com.hitit.app.model

import kotlin.test.Test
import kotlin.test.assertEquals
import kotlin.test.assertNotEquals
import kotlin.test.assertTrue

class GameSessionTest {

    @Test
    fun newSession_hasZeroScore() {
        val session = GameSession()
        assertEquals(0, session.score)
        assertEquals(0, session.total)
    }

    @Test
    fun newSession_hasUniqueId() {
        val session1 = GameSession()
        val session2 = GameSession()
        assertNotEquals(session1.id, session2.id)
    }

    @Test
    fun newSession_hasEmptyCardsList() {
        val session = GameSession()
        assertTrue(session.cards.isEmpty())
    }

    @Test
    fun addCard_correct_increasesScore() {
        val session = GameSession()
        val card = createPlayedCard(CardResult.CORRECT)

        val updatedSession = session.addCard(card)

        assertEquals(1, updatedSession.score)
        assertEquals(1, updatedSession.total)
    }

    @Test
    fun addCard_incorrect_doesNotIncreaseScore() {
        val session = GameSession()
        val card = createPlayedCard(CardResult.INCORRECT)

        val updatedSession = session.addCard(card)

        assertEquals(0, updatedSession.score)
        assertEquals(1, updatedSession.total)
    }

    @Test
    fun addCard_skipped_doesNotIncreaseScore() {
        val session = GameSession()
        val card = createPlayedCard(CardResult.SKIPPED)

        val updatedSession = session.addCard(card)

        assertEquals(0, updatedSession.score)
        assertEquals(1, updatedSession.total)
    }

    @Test
    fun addMultipleCards_calculatesScoreCorrectly() {
        var session = GameSession()

        session = session.addCard(createPlayedCard(CardResult.CORRECT))
        session = session.addCard(createPlayedCard(CardResult.INCORRECT))
        session = session.addCard(createPlayedCard(CardResult.CORRECT))
        session = session.addCard(createPlayedCard(CardResult.SKIPPED))
        session = session.addCard(createPlayedCard(CardResult.CORRECT))

        assertEquals(3, session.score)
        assertEquals(5, session.total)
    }

    @Test
    fun addCard_preservesSessionId() {
        val session = GameSession()
        val originalId = session.id

        val updatedSession = session.addCard(createPlayedCard(CardResult.CORRECT))

        assertEquals(originalId, updatedSession.id)
    }

    @Test
    fun addCard_preservesStartedAt() {
        val session = GameSession()
        val originalStartedAt = session.startedAt

        val updatedSession = session.addCard(createPlayedCard(CardResult.CORRECT))

        assertEquals(originalStartedAt, updatedSession.startedAt)
    }

    @Test
    fun addCard_appendsToCardsList() {
        var session = GameSession()

        val card1 = createPlayedCard(CardResult.CORRECT, "card1")
        val card2 = createPlayedCard(CardResult.INCORRECT, "card2")

        session = session.addCard(card1)
        session = session.addCard(card2)

        assertEquals(2, session.cards.size)
        assertEquals("card1", session.cards[0].cardId)
        assertEquals("card2", session.cards[1].cardId)
    }

    @Test
    fun playedCard_hasCorrectResult() {
        val correctCard = createPlayedCard(CardResult.CORRECT)
        val incorrectCard = createPlayedCard(CardResult.INCORRECT)
        val skippedCard = createPlayedCard(CardResult.SKIPPED)

        assertEquals(CardResult.CORRECT, correctCard.result)
        assertEquals(CardResult.INCORRECT, incorrectCard.result)
        assertEquals(CardResult.SKIPPED, skippedCard.result)
    }

    private fun createPlayedCard(
        result: CardResult,
        cardId: String = "test-card"
    ): PlayedCard {
        return PlayedCard(
            cardId = cardId,
            trackId = "track-123",
            title = "Test Song",
            artist = "Test Artist",
            year = 2020,
            result = result
        )
    }
}
