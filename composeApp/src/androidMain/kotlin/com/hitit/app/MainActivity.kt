package com.hitit.app

import android.graphics.Color
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.SystemBarStyle
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import com.hitit.app.di.allModules
import com.hitit.app.settings.DebugSettings
import com.hitit.app.settings.DebugSettingsStore
import org.koin.android.ext.koin.androidContext
import org.koin.compose.KoinApplication

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        // Set debug flag from application info
        AppBuildConfig.applicationInfo = applicationInfo
        // Initialize debug settings store
        DebugSettings.initialize(DebugSettingsStore(this))
        // Set status bar to match primary orange color
        enableEdgeToEdge(
            statusBarStyle = SystemBarStyle.dark(Color.parseColor("#FF6B35"))
        )
        setContent {
            KoinApplication(
                application = {
                    androidContext(this@MainActivity)
                    modules(allModules)
                }
            ) {
                App()
            }
        }
    }
}
