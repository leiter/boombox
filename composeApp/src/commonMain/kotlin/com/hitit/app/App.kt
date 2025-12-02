package com.hitit.app

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.hitit.app.ui.screens.HomeScreen
import com.hitit.app.ui.screens.ScannerScreen
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
                startDestination = Screen.Home.route
            ) {
                composable(Screen.Home.route) {
                    HomeScreen(
                        onStartScanning = {
                            navController.navigate(Screen.Scanner.route)
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
            }
        }
    }
}

sealed class Screen(val route: String) {
    data object Home : Screen("home")
    data object Scanner : Screen("scanner")
}

expect fun getPlatformName(): String
