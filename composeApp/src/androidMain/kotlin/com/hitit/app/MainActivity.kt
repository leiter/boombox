package com.hitit.app

import android.graphics.Color
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.SystemBarStyle
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import com.hitit.app.settings.DebugSettings
import com.hitit.app.settings.DebugSettingsStore
import androidx.core.graphics.toColorInt

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
