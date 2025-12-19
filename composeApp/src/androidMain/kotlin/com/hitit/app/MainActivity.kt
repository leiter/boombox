package com.hitit.app

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.SystemBarStyle
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.core.graphics.toColorInt
import com.hitit.app.settings.DebugSettings
import com.hitit.app.settings.DebugSettingsStore

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        // Initialize debug settings store
        DebugSettings.initialize(DebugSettingsStore(this))
        // Set system bars to match Neon Cyber dark purple background
        val backgroundLight = "#1A0A2E".toColorInt()
        val backgroundDark = "#0D0221".toColorInt()
        enableEdgeToEdge(
            statusBarStyle = SystemBarStyle.dark(backgroundLight),
            navigationBarStyle = SystemBarStyle.dark(backgroundDark)
        )
        setContent {
            App()
        }
    }
}
