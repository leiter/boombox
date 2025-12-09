package com.hitit.app.ui.screens

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.systemBars
import androidx.compose.foundation.layout.windowInsetsPadding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.hitit.app.settings.DebugSettings

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun DebugSettingsScreen(
    onBack: () -> Unit
) {
    val settingsState by DebugSettings.state.collectAsState()

    Scaffold(
        modifier = Modifier.windowInsetsPadding(WindowInsets.systemBars),
        topBar = {
            TopAppBar(
                title = { Text("Debug Settings") },
                navigationIcon = {
                    IconButton(onClick = onBack) {
                        Icon(
                            imageVector = Icons.AutoMirrored.Filled.ArrowBack,
                            contentDescription = "Back"
                        )
                    }
                },
                colors = TopAppBarDefaults.topAppBarColors(
                    containerColor = MaterialTheme.colorScheme.primary,
                    titleContentColor = MaterialTheme.colorScheme.onPrimary,
                    navigationIconContentColor = MaterialTheme.colorScheme.onPrimary
                )
            )
        }
    ) { padding ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(padding)
                .padding(16.dp),
            verticalArrangement = Arrangement.spacedBy(16.dp)
        ) {
            Text(
                text = "Flip Detection",
                style = MaterialTheme.typography.titleMedium
            )

            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                Text("Auto-flip for testing")
                Switch(
                    checked = settingsState.autoFlipEnabled,
                    onCheckedChange = { DebugSettings.setAutoFlipEnabled(it) }
                )
            }

            Text(
                text = "Auto-flip delay: ${settingsState.autoFlipDelayMs}ms",
                style = MaterialTheme.typography.bodyMedium
            )
            Slider(
                value = settingsState.autoFlipDelayMs.toFloat(),
                onValueChange = { DebugSettings.setAutoFlipDelayMs(it.toLong()) },
                valueRange = 1000f..10000f,
                steps = 8,
                enabled = settingsState.autoFlipEnabled
            )

            HorizontalDivider()

            Text(
                text = "Music Playback",
                style = MaterialTheme.typography.titleMedium
            )

            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                Column(modifier = Modifier.weight(1f)) {
                    Text("Use Deezer app")
                    Text(
                        text = if (settingsState.useDeezerDeeplink)
                            "Opens full song in Deezer"
                        else
                            "Plays 30-second preview in-app",
                        style = MaterialTheme.typography.bodySmall,
                        color = MaterialTheme.colorScheme.onSurfaceVariant
                    )
                }
                Switch(
                    checked = settingsState.useDeezerDeeplink,
                    onCheckedChange = { DebugSettings.setUseDeezerDeeplink(it) }
                )
            }

            HorizontalDivider()

            Text(
                text = "Info",
                style = MaterialTheme.typography.titleMedium
            )

            Text(
                text = "Settings are persisted and take effect immediately.",
                style = MaterialTheme.typography.bodySmall,
                color = MaterialTheme.colorScheme.onSurfaceVariant
            )
        }
    }
}
