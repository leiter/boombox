package com.hitit.app.ui

import androidx.compose.ui.test.assertIsDisplayed
import androidx.compose.ui.test.junit4.createComposeRule
import androidx.compose.ui.test.onNodeWithText
import androidx.compose.ui.test.performClick
import com.hitit.app.ui.screens.NowPlayingScreen
import com.hitit.app.ui.theme.DukeStarTheme
import org.junit.Rule
import org.junit.Test

class NowPlayingScreenTest {

    @get:Rule
    val composeTestRule = createComposeRule()

    @Test
    fun nowPlayingScreen_displaysTrackInfo() {
        composeTestRule.setContent {
            DukeStarTheme {
                NowPlayingScreen(
                    title = "Test Song",
                    artist = "Test Artist",
                    year = 2020,
                    onNextCard = {},
                    onClose = {}
                )
            }
        }

        composeTestRule.onNodeWithText("Test Song").assertIsDisplayed()
        composeTestRule.onNodeWithText("Test Artist").assertIsDisplayed()
        composeTestRule.onNodeWithText("2020").assertIsDisplayed()
    }

    @Test
    fun nowPlayingScreen_displaysScoreButtons() {
        composeTestRule.setContent {
            DukeStarTheme {
                NowPlayingScreen(
                    title = "Test Song",
                    artist = "Test Artist",
                    year = 2020,
                    onNextCard = {},
                    onClose = {}
                )
            }
        }

        composeTestRule.onNodeWithText("Correct").assertIsDisplayed()
        composeTestRule.onNodeWithText("Incorrect").assertIsDisplayed()
        composeTestRule.onNodeWithText("Skip").assertIsDisplayed()
    }

    @Test
    fun nowPlayingScreen_displaysNextCardButton() {
        composeTestRule.setContent {
            DukeStarTheme {
                NowPlayingScreen(
                    title = "Test Song",
                    artist = "Test Artist",
                    year = 2020,
                    onNextCard = {},
                    onClose = {}
                )
            }
        }

        composeTestRule.onNodeWithText("NEXT CARD").assertIsDisplayed()
    }

    @Test
    fun nowPlayingScreen_displaysScoreBadge_whenTotalGreaterThanZero() {
        composeTestRule.setContent {
            DukeStarTheme {
                NowPlayingScreen(
                    title = "Test Song",
                    artist = "Test Artist",
                    year = 2020,
                    score = 3,
                    total = 5,
                    onNextCard = {},
                    onClose = {}
                )
            }
        }

        composeTestRule.onNodeWithText("Score: 3 / 5").assertIsDisplayed()
    }

    @Test
    fun nowPlayingScreen_correctButtonCallsCallback() {
        var correctCalled = false

        composeTestRule.setContent {
            DukeStarTheme {
                NowPlayingScreen(
                    title = "Test Song",
                    artist = "Test Artist",
                    year = 2020,
                    onCorrect = { correctCalled = true },
                    onNextCard = {},
                    onClose = {}
                )
            }
        }

        composeTestRule.onNodeWithText("Correct").performClick()

        assert(correctCalled) { "onCorrect callback should have been called" }
    }

    @Test
    fun nowPlayingScreen_incorrectButtonCallsCallback() {
        var incorrectCalled = false

        composeTestRule.setContent {
            DukeStarTheme {
                NowPlayingScreen(
                    title = "Test Song",
                    artist = "Test Artist",
                    year = 2020,
                    onIncorrect = { incorrectCalled = true },
                    onNextCard = {},
                    onClose = {}
                )
            }
        }

        composeTestRule.onNodeWithText("Incorrect").performClick()

        assert(incorrectCalled) { "onIncorrect callback should have been called" }
    }

    @Test
    fun nowPlayingScreen_skipButtonCallsCallback() {
        var skipCalled = false

        composeTestRule.setContent {
            DukeStarTheme {
                NowPlayingScreen(
                    title = "Test Song",
                    artist = "Test Artist",
                    year = 2020,
                    onSkip = { skipCalled = true },
                    onNextCard = {},
                    onClose = {}
                )
            }
        }

        composeTestRule.onNodeWithText("Skip").performClick()

        assert(skipCalled) { "onSkip callback should have been called" }
    }

    @Test
    fun nowPlayingScreen_displaysUnknownTrack_whenTitleIsNull() {
        composeTestRule.setContent {
            DukeStarTheme {
                NowPlayingScreen(
                    title = null,
                    artist = null,
                    year = null,
                    onNextCard = {},
                    onClose = {}
                )
            }
        }

        composeTestRule.onNodeWithText("Unknown Track").assertIsDisplayed()
        composeTestRule.onNodeWithText("Unknown Artist").assertIsDisplayed()
    }
}
