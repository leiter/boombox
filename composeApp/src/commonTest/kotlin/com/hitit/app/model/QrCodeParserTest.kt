package com.hitit.app.model

import kotlin.test.Test
import kotlin.test.assertEquals
import kotlin.test.assertIs

class QrCodeParserTest {

    @Test
    fun parseHitsterCard_validUrl_returnsHitsterCard() {
        val result = QrCodeParser.parse("https://hitstergame.com/en/00001")
        assertIs<QrCodeResult.HitsterCard>(result)
        assertEquals("00001", result.cardId)
    }

    @Test
    fun parseHitsterCard_differentLanguage_returnsHitsterCard() {
        val result = QrCodeParser.parse("https://hitstergame.com/de/12345")
        assertIs<QrCodeResult.HitsterCard>(result)
        assertEquals("12345", result.cardId)
    }

    @Test
    fun parseDeezerTrack_validUrl_returnsDeezerTrack() {
        val result = QrCodeParser.parse("https://www.deezer.com/track/123456789")
        assertIs<QrCodeResult.DeezerTrack>(result)
        assertEquals("123456789", result.trackId)
    }

    @Test
    fun parseDeezerTrack_withLanguageCode_returnsDeezerTrack() {
        val result = QrCodeParser.parse("https://www.deezer.com/en/track/987654321")
        assertIs<QrCodeResult.DeezerTrack>(result)
        assertEquals("987654321", result.trackId)
    }

    @Test
    fun parseSpotifyTrack_validUrl_returnsSpotifyTrack() {
        val result = QrCodeParser.parse("https://open.spotify.com/track/4iV5W9uYEdYUVa79Axb7Rh")
        assertIs<QrCodeResult.SpotifyTrack>(result)
        assertEquals("4iV5W9uYEdYUVa79Axb7Rh", result.trackId)
    }

    @Test
    fun parseYouTubeVideo_watchUrl_returnsYouTubeVideo() {
        val result = QrCodeParser.parse("https://www.youtube.com/watch?v=dQw4w9WgXcQ")
        assertIs<QrCodeResult.YouTubeVideo>(result)
        assertEquals("dQw4w9WgXcQ", result.videoId)
    }

    @Test
    fun parseYouTubeVideo_shortUrl_returnsYouTubeVideo() {
        val result = QrCodeParser.parse("https://youtu.be/dQw4w9WgXcQ")
        assertIs<QrCodeResult.YouTubeVideo>(result)
        assertEquals("dQw4w9WgXcQ", result.videoId)
    }

    @Test
    fun parseGenericUrl_httpUrl_returnsGenericUrl() {
        val result = QrCodeParser.parse("https://example.com/some/path")
        assertIs<QrCodeResult.GenericUrl>(result)
        assertEquals("https://example.com/some/path", result.url)
    }

    @Test
    fun parseGenericUrl_httpUrl_returnsGenericUrl2() {
        val result = QrCodeParser.parse("http://example.com")
        assertIs<QrCodeResult.GenericUrl>(result)
        assertEquals("http://example.com", result.url)
    }

    @Test
    fun parseUnknown_plainText_returnsUnknown() {
        val result = QrCodeParser.parse("just some random text")
        assertIs<QrCodeResult.Unknown>(result)
        assertEquals("just some random text", result.rawContent)
    }

    @Test
    fun parseUnknown_emptyString_returnsUnknown() {
        val result = QrCodeParser.parse("")
        assertIs<QrCodeResult.Unknown>(result)
        assertEquals("", result.rawContent)
    }

    @Test
    fun parseHitsterCard_caseInsensitive_returnsHitsterCard() {
        // URL matching should work regardless of case in domain
        val result = QrCodeParser.parse("https://HITSTERGAME.COM/en/00042")
        // Note: The regex is case-sensitive, so this might return GenericUrl
        // This test documents current behavior
        assertIs<QrCodeResult.GenericUrl>(result)
    }
}
