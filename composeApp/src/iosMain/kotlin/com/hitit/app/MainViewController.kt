package com.hitit.app

import androidx.compose.ui.window.ComposeUIViewController
import com.hitit.app.di.allModules
import org.koin.compose.KoinApplication

fun MainViewController() = ComposeUIViewController {
    KoinApplication(
        application = {
            modules(allModules)
        }
    ) {
        App()
    }
}
