package com.hitit.app

import android.graphics.Color
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.SystemBarStyle
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import com.hitit.app.settings.DebugSettings
import com.hitit.app.settings.DebugSettingsStore

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        // Set debug flag from application info
        AppBuildConfig.applicationInfo = applicationInfo
        // Initialize debug settings store
        DebugSettings.initialize(DebugSettingsStore(this))
        // Set status bar and navigation bar to match primary orange color
        val orangeColor = Color.parseColor("#FF6B35")
        enableEdgeToEdge(
            statusBarStyle = SystemBarStyle.dark(orangeColor),
            navigationBarStyle = SystemBarStyle.dark(orangeColor)
        )
        setContent {
            App()
        }
    }
}
