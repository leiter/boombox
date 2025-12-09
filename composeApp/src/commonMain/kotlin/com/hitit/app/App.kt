package com.hitit.app

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.hitit.app.ui.screens.DebugSettingsScreen
import com.hitit.app.ui.screens.HomeScreen
import com.hitit.app.ui.screens.ScannerScreen
import com.hitit.app.ui.screens.SplashScreen
import com.hitit.app.ui.theme.HitItTheme

@Composable
fun App() {
    HitItTheme {
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
                        onOpenDebugSettings = {
                            navController.navigate(Screen.DebugSettings.route)
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

                composable(Screen.DebugSettings.route) {
                    DebugSettingsScreen(
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
    data object DebugSettings : Screen("debug_settings")
}

expect fun getPlatformName(): String
