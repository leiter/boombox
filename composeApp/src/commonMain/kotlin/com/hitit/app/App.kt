package com.hitit.app

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.hitit.app.ui.screens.CardEditScreen
import com.hitit.app.ui.screens.CardSetEditScreen
import com.hitit.app.ui.screens.CardSetListScreen
import com.hitit.app.ui.screens.DebugSettingsScreen
import com.hitit.app.ui.screens.HomeScreen
import com.hitit.app.ui.screens.PdfPreviewScreen
import com.hitit.app.ui.screens.PlaylistImportScreen
import com.hitit.app.ui.screens.ScannerScreen
import com.hitit.app.ui.screens.SettingsScreen
import com.hitit.app.ui.screens.SplashScreen
import com.hitit.app.ui.screens.TrackSearchScreen
import com.hitit.app.ui.theme.DukeStarTheme

@Composable
fun App() {
    DukeStarTheme {
        Surface(
            modifier = Modifier.fillMaxSize(),
            color = MaterialTheme.colorScheme.background
        ) {
            val navController = rememberNavController()

            NavHost(
                navController = navController,
                startDestination = Screen.Splash.route
            ) {
                composable(Screen.Splash.route) {
                    SplashScreen(
                        onSplashComplete = {
                            navController.navigate(Screen.Home.route) {
                                // Remove splash from back stack so it's not an exit destination
                                popUpTo(Screen.Splash.route) { inclusive = true }
                            }
                        }
                    )
                }

                composable(Screen.Home.route) {
                    HomeScreen(
                        onStartScanning = {
                            navController.navigate(Screen.Scanner.route)
                        },
                        onOpenSettings = {
                            navController.navigate(Screen.Settings.route)
                        },
                        onOpenDebugSettings = {
                            navController.navigate(Screen.DebugSettings.route)
                        },
                        onOpenCardSets = {
                            navController.navigate(Screen.CardSetList.route)
                        }
                    )
                }

                composable(Screen.Scanner.route) {
                    ScannerScreen(
                        onBackToHome = {
                            navController.popBackStack()
                        }
                    )
                }

                composable(Screen.Settings.route) {
                    SettingsScreen(
                        onBack = {
                            navController.popBackStack()
                        }
                    )
                }

                composable(Screen.DebugSettings.route) {
                    DebugSettingsScreen(
                        onBack = {
                            navController.popBackStack()
                        }
                    )
                }

                // Card Set screens
                composable(Screen.CardSetList.route) {
                    CardSetListScreen(
                        onBack = {
                            navController.popBackStack()
                        },
                        onImportPlaylist = {
                            navController.navigate(Screen.PlaylistImport.route)
                        },
                        onEditCardSet = { cardSetId ->
                            navController.navigate(Screen.CardSetEdit.createRoute(cardSetId))
                        },
                        onExportPdf = { cardSetId ->
                            navController.navigate(Screen.PdfPreview.createRoute(cardSetId))
                        },
                        onPlayCardSet = { cardSetId ->
                            navController.navigate(Screen.Scanner.route)
                        }
                    )
                }

                composable(Screen.PlaylistImport.route) {
                    PlaylistImportScreen(
                        onBack = {
                            navController.popBackStack()
                        },
                        onCardSetCreated = { cardSetId ->
                            navController.navigate(Screen.CardSetEdit.createRoute(cardSetId)) {
                                popUpTo(Screen.CardSetList.route)
                            }
                        }
                    )
                }

                composable(Screen.CardSetEdit.route) { backStackEntry ->
                    val cardSetId = backStackEntry.arguments?.getString("cardSetId") ?: return@composable
                    CardSetEditScreen(
                        cardSetId = cardSetId,
                        onBack = {
                            navController.popBackStack()
                        },
                        onEditCard = { setId, cardId ->
                            navController.navigate(Screen.CardEdit.createRoute(setId, cardId))
                        },
                        onSearchTracks = {
                            navController.navigate(Screen.TrackSearch.createRoute(cardSetId))
                        },
                        onExportPdf = {
                            navController.navigate(Screen.PdfPreview.createRoute(cardSetId))
                        }
                    )
                }

                composable(Screen.CardEdit.route) { backStackEntry ->
                    val cardSetId = backStackEntry.arguments?.getString("cardSetId") ?: return@composable
                    val cardId = backStackEntry.arguments?.getString("cardId") ?: return@composable
                    CardEditScreen(
                        cardSetId = cardSetId,
                        cardId = cardId,
                        onBack = {
                            navController.popBackStack()
                        }
                    )
                }

                composable(Screen.TrackSearch.route) { backStackEntry ->
                    val cardSetId = backStackEntry.arguments?.getString("cardSetId") ?: return@composable
                    TrackSearchScreen(
                        cardSetId = cardSetId,
                        onBack = {
                            navController.popBackStack()
                        }
                    )
                }

                composable(Screen.PdfPreview.route) { backStackEntry ->
                    val cardSetId = backStackEntry.arguments?.getString("cardSetId") ?: return@composable
                    PdfPreviewScreen(
                        cardSetId = cardSetId,
                        onBack = {
                            navController.popBackStack()
                        }
                    )
                }
            }
        }
    }
}

sealed class Screen(val route: String) {
    data object Splash : Screen("splash")
    data object Home : Screen("home")
    data object Scanner : Screen("scanner")
    data object Settings : Screen("settings")
    data object DebugSettings : Screen("debug_settings")
    data object CardSetList : Screen("card_sets")
    data object PlaylistImport : Screen("playlist_import")
    data object CardSetEdit : Screen("card_set_edit/{cardSetId}") {
        fun createRoute(cardSetId: String) = "card_set_edit/$cardSetId"
    }
    data object CardEdit : Screen("card_edit/{cardSetId}/{cardId}") {
        fun createRoute(cardSetId: String, cardId: String) = "card_edit/$cardSetId/$cardId"
    }
    data object TrackSearch : Screen("track_search/{cardSetId}") {
        fun createRoute(cardSetId: String) = "track_search/$cardSetId"
    }
    data object PdfPreview : Screen("pdf_preview/{cardSetId}") {
        fun createRoute(cardSetId: String) = "pdf_preview/$cardSetId"
    }
}

