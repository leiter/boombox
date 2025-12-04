package com.hitit.app.repository

import com.hitit.app.model.HitsterCard
import kotlinx.coroutines.delay

/**
 * Mock implementation of HitsterCardRepository.
 * Simulates server response with pre-loaded Deezer data.
 */
class MockHitsterCardRepository : HitsterCardRepository {

    // Simulated network delay (ms)
    private val simulatedDelay = 300L

    // Mock database of Hitster cards with Deezer matches
    private val cardDatabase: Map<String, HitsterCard> = mapOf(
        "00001" to HitsterCard(
            hitsterId = "00001",
            title = "Lean On",
            artist = "Major Lazer ft. Snake & Mo",
            year = 2015,
            deezerId = 445800112,
            deezerTitle = "Lean On",
            deezerArtist = "Major Lazer",
            deezerAlbum = "Peace Is The Mission (Remixes)"
        ),
        "00002" to HitsterCard(
            hitsterId = "00002",
            title = "Einmal um die Welt",
            artist = "Cro",
            year = 2012,
            deezerId = 3309927701,
            deezerTitle = "Einmal um die Welt",
            deezerArtist = "CRO",
            deezerAlbum = "Raop"
        ),
        "00003" to HitsterCard(
            hitsterId = "00003",
            title = "Ich will keine Schokolade",
            artist = "Trude Herr",
            year = 1960,
            deezerId = 2322567,
            deezerTitle = "Ich will keine Schokolade",
            deezerArtist = "Trude Herr",
            deezerAlbum = "Ich Will Keine Schokolade"
        ),
        "00004" to HitsterCard(
            hitsterId = "00004",
            title = "80 Millionen",
            artist = "Max Giesinger",
            year = 2016,
            deezerId = 518107802,
            deezerTitle = "80 Millionen",
            deezerArtist = "Max Giesinger",
            deezerAlbum = "80 Millionen"
        ),
        "00005" to HitsterCard(
            hitsterId = "00005",
            title = "Sugar Baby",
            artist = "Peter Kraus",
            year = 1958,
            deezerId = 16638774,
            deezerTitle = "Sugar Baby",
            deezerArtist = "Peter Kraus",
            deezerAlbum = "Sugar Sugar Baby"
        ),
        "00006" to HitsterCard(
            hitsterId = "00006",
            title = "Über den Wolken",
            artist = "Reinhard Mey",
            year = 1974,
            deezerId = 3297738,
            deezerTitle = "Über den Wolken",
            deezerArtist = "Reinhard Mey",
            deezerAlbum = "Die Grossen Erfolge"
        ),
        "00010" to HitsterCard(
            hitsterId = "00010",
            title = "Orinoco Flow",
            artist = "Enya",
            year = 1988,
            deezerId = 65728152,
            deezerTitle = "Orinoco Flow",
            deezerArtist = "Enya",
            deezerAlbum = "Paint the Sky with Stars"
        ),
        "00011" to HitsterCard(
            hitsterId = "00011",
            title = "Don't Stop Believin'",
            artist = "Journey",
            year = 1981,
            deezerId = 625643,
            deezerTitle = "Don't Stop Believin'",
            deezerArtist = "Journey",
            deezerAlbum = "The Essential Journey"
        ),
        "00012" to HitsterCard(
            hitsterId = "00012",
            title = "Auf uns",
            artist = "Andreas Bourani",
            year = 2014,
            deezerId = 77045434,
            deezerTitle = "Auf uns",
            deezerArtist = "Andreas Bourani",
            deezerAlbum = "Auf uns"
        ),
        "00013" to HitsterCard(
            hitsterId = "00013",
            title = "What is Love",
            artist = "Haddaway",
            year = 1993,
            deezerId = 2688767492,
            deezerTitle = "What Is Love",
            deezerArtist = "Haddaway",
            deezerAlbum = "What Is Love"
        ),
        "00014" to HitsterCard(
            hitsterId = "00014",
            title = "Friday I'm In Love",
            artist = "The Cure",
            year = 1992,
            deezerId = 1123014,
            deezerTitle = "Friday I'm In Love",
            deezerArtist = "The Cure",
            deezerAlbum = "Wish"
        ),
        "00015" to HitsterCard(
            hitsterId = "00015",
            title = "The Best",
            artist = "Tina Turner",
            year = 1989,
            deezerId = 3142379,
            deezerTitle = "The Best",
            deezerArtist = "Tina Turner",
            deezerAlbum = "Tina!"
        ),
        "00016" to HitsterCard(
            hitsterId = "00016",
            title = "China in Your Hand",
            artist = "T'Pau",
            year = 1987,
            deezerId = 3152784,
            deezerTitle = "China In Your Hand",
            deezerArtist = "T'pau",
            deezerAlbum = "Heart And Soul - The Very Best Of T'Pau"
        ),
        "00017" to HitsterCard(
            hitsterId = "00017",
            title = "One More Time",
            artist = "Daft Punk",
            year = 2000,
            deezerId = 3135553,
            deezerTitle = "One More Time",
            deezerArtist = "Daft Punk",
            deezerAlbum = "Discovery"
        ),
        "00018" to HitsterCard(
            hitsterId = "00018",
            title = "7 Years",
            artist = "Lukas Graham",
            year = 2015,
            deezerId = 101742167,
            deezerTitle = "7 Years",
            deezerArtist = "Lukas Graham",
            deezerAlbum = "Lukas Graham (Blue Album)"
        )
    )

    override suspend fun getCardById(cardId: String): HitsterCard? {
        // Simulate network delay
        delay(simulatedDelay)

        // Normalize card ID (handle both "1" and "00001" formats)
        val normalizedId = cardId.padStart(5, '0')

        return cardDatabase[normalizedId]
    }
}
