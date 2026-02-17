package com.hitit.app.ui

import androidx.compose.ui.test.assertIsDisplayed
import androidx.compose.ui.test.junit4.createComposeRule
import androidx.compose.ui.test.onNodeWithText
import androidx.compose.ui.test.performClick
import com.hitit.app.ui.screens.HomeScreen
import com.hitit.app.ui.theme.DukeStarTheme
import org.junit.Rule
import org.junit.Test

class HomeScreenTest {

    @get:Rule
    val composeTestRule = createComposeRule()

    @Test
    fun homeScreen_displaysAppTitle() {
        composeTestRule.setContent {
            DukeStarTheme {
                HomeScreen(
                    onStartScanning = {},
                    onOpenDebugSettings = {}
                )
            }
        }

        composeTestRule.onNodeWithText("DukeStar").assertIsDisplayed()
    }

    @Test
    fun homeScreen_displaysStartGameButton() {
        composeTestRule.setContent {
            DukeStarTheme {
                HomeScreen(
                    onStartScanning = {},
                    onOpenDebugSettings = {}
                )
            }
        }

        // The button text is localized, so we check for the default English text
        composeTestRule.onNodeWithText("Start Game").assertIsDisplayed()
    }

    @Test
    fun homeScreen_displaysHowToPlaySection() {
        composeTestRule.setContent {
            DukeStarTheme {
                HomeScreen(
                    onStartScanning = {},
                    onOpenDebugSettings = {}
                )
            }
        }

        composeTestRule.onNodeWithText("How to Play").assertIsDisplayed()
    }

    @Test
    fun homeScreen_howToPlayExpandsOnClick() {
        composeTestRule.setContent {
            DukeStarTheme {
                HomeScreen(
                    onStartScanning = {},
                    onOpenDebugSettings = {}
                )
            }
        }

        // Click on "How to Play" to expand
        composeTestRule.onNodeWithText("How to Play").performClick()

        // Check that instruction step 1 is now visible
        composeTestRule.onNodeWithText("1. Scan a Hitster card QR code", substring = true).assertIsDisplayed()
    }
}
