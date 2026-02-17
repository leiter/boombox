package com.hitit.app.ui.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.hitit.app.model.GameSession
import com.hitit.app.service.GameSessionStore
import com.hitit.app.settings.DebugSettings
import com.hitit.app.ui.theme.BackgroundDark
import com.hitit.app.ui.theme.BackgroundLight
import com.hitit.app.ui.theme.Primary
import com.hitit.app.ui.theme.Secondary
import com.hitit.app.ui.theme.SurfaceBorder
import com.hitit.app.ui.theme.SurfaceLight
import com.hitit.app.ui.theme.TextSecondary
import hitit.composeapp.generated.resources.*
import org.jetbrains.compose.resources.stringResource
import org.koin.compose.koinInject

@Composable
fun SettingsScreen(
    onBack: () -> Unit,
    gameSessionStore: GameSessionStore = koinInject()
) {
    val settingsState by DebugSettings.state.collectAsState()
    var showClearSessionDialog by remember { mutableStateOf(false) }
    var currentSession by remember { mutableStateOf(gameSessionStore.loadCurrentSession()) }

    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(Brush.verticalGradient(listOf(BackgroundLight, BackgroundDark)))
            .windowInsetsPadding(WindowInsets.systemBars)
    ) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .verticalScroll(rememberScrollState())
        ) {
            // Top bar
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(16.dp),
                verticalAlignment = Alignment.CenterVertically
            ) {
                IconButton(onClick = onBack) {
                    Icon(
                        imageVector = Icons.AutoMirrored.Filled.ArrowBack,
                        contentDescription = stringResource(Res.string.close),
                        tint = Color.White
                    )
                }
                Spacer(modifier = Modifier.width(8.dp))
                Text(
                    text = stringResource(Res.string.settings),
                    fontSize = 20.sp,
                    fontWeight = FontWeight.Bold,
                    color = Color.White
                )
            }

            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 16.dp),
                verticalArrangement = Arrangement.spacedBy(16.dp)
            ) {
                // Playback Settings Section
                SettingsSection(title = stringResource(Res.string.settings_playback)) {
                    SettingsCard {
                        Row(
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(16.dp),
                            horizontalArrangement = Arrangement.SpaceBetween,
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            Column(modifier = Modifier.weight(1f)) {
                                Text(
                                    text = stringResource(Res.string.settings_use_full_version),
                                    color = Color.White,
                                    fontWeight = FontWeight.Medium
                                )
                                Text(
                                    text = if (settingsState.useFullVersion)
                                        stringResource(Res.string.settings_full_version_desc)
                                    else
                                        stringResource(Res.string.settings_preview_desc),
                                    color = TextSecondary,
                                    fontSize = 12.sp
                                )
                            }
                            Switch(
                                checked = settingsState.useFullVersion,
                                onCheckedChange = { DebugSettings.setUseFullVersion(it) },
                                colors = SwitchDefaults.colors(
                                    checkedThumbColor = Color.White,
                                    checkedTrackColor = Primary,
                                    uncheckedThumbColor = Color.White,
                                    uncheckedTrackColor = SurfaceLight
                                )
                            )
                        }
                    }
                }

                // Game Session Section
                SettingsSection(title = stringResource(Res.string.settings_game)) {
                    SettingsCard {
                        Column(
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(16.dp)
                        ) {
                            // Current session info
                            if (currentSession != null && currentSession!!.total > 0) {
                                Text(
                                    text = stringResource(Res.string.settings_current_session),
                                    color = Color.White,
                                    fontWeight = FontWeight.Medium
                                )
                                Spacer(modifier = Modifier.height(8.dp))
                                Text(
                                    text = stringResource(
                                        Res.string.score_display,
                                        currentSession!!.score,
                                        currentSession!!.total
                                    ),
                                    color = Secondary,
                                    fontSize = 18.sp,
                                    fontWeight = FontWeight.Bold
                                )
                                Spacer(modifier = Modifier.height(16.dp))

                                // Clear session button
                                Box(
                                    modifier = Modifier
                                        .fillMaxWidth()
                                        .height(44.dp)
                                        .clip(RoundedCornerShape(22.dp))
                                        .background(Color(0xFFF44336).copy(alpha = 0.2f))
                                        .border(1.dp, Color(0xFFF44336), RoundedCornerShape(22.dp))
                                        .clickable { showClearSessionDialog = true },
                                    contentAlignment = Alignment.Center
                                ) {
                                    Text(
                                        text = stringResource(Res.string.settings_clear_session),
                                        color = Color(0xFFF44336),
                                        fontWeight = FontWeight.Medium
                                    )
                                }
                            } else {
                                Text(
                                    text = stringResource(Res.string.settings_no_session),
                                    color = TextSecondary,
                                    fontSize = 14.sp
                                )
                            }
                        }
                    }
                }

                // About Section
                SettingsSection(title = stringResource(Res.string.settings_about)) {
                    SettingsCard {
                        Column(
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(16.dp)
                        ) {
                            SettingsRow(
                                label = stringResource(Res.string.settings_version),
                                value = "1.0"
                            )
                            HorizontalDivider(
                                modifier = Modifier.padding(vertical = 12.dp),
                                color = SurfaceBorder
                            )
                            SettingsRow(
                                label = stringResource(Res.string.settings_build),
                                value = "27"
                            )
                        }
                    }
                }

                Spacer(modifier = Modifier.height(32.dp))
            }
        }

        // Clear session confirmation dialog
        if (showClearSessionDialog) {
            AlertDialog(
                onDismissRequest = { showClearSessionDialog = false },
                title = {
                    Text(stringResource(Res.string.settings_clear_session_title))
                },
                text = {
                    Text(stringResource(Res.string.settings_clear_session_message))
                },
                confirmButton = {
                    TextButton(
                        onClick = {
                            gameSessionStore.clearSession()
                            currentSession = null
                            showClearSessionDialog = false
                        }
                    ) {
                        Text(
                            stringResource(Res.string.settings_clear),
                            color = Color(0xFFF44336)
                        )
                    }
                },
                dismissButton = {
                    TextButton(onClick = { showClearSessionDialog = false }) {
                        Text(stringResource(Res.string.settings_cancel))
                    }
                },
                containerColor = SurfaceLight
            )
        }
    }
}

@Composable
private fun SettingsSection(
    title: String,
    content: @Composable () -> Unit
) {
    Column {
        Text(
            text = title,
            color = Secondary,
            fontSize = 12.sp,
            fontWeight = FontWeight.Bold,
            letterSpacing = 1.sp,
            modifier = Modifier.padding(start = 4.dp, bottom = 8.dp)
        )
        content()
    }
}

@Composable
private fun SettingsCard(
    content: @Composable () -> Unit
) {
    Surface(
        modifier = Modifier
            .fillMaxWidth()
            .clip(RoundedCornerShape(12.dp))
            .border(1.dp, SurfaceBorder, RoundedCornerShape(12.dp)),
        color = SurfaceLight
    ) {
        content()
    }
}

@Composable
private fun SettingsRow(
    label: String,
    value: String
) {
    Row(
        modifier = Modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.SpaceBetween
    ) {
        Text(
            text = label,
            color = Color.White
        )
        Text(
            text = value,
            color = TextSecondary
        )
    }
}
