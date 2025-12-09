package com.hitit.app.ui.screens

import androidx.compose.animation.*
import androidx.compose.animation.core.*
import androidx.compose.foundation.layout.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.KeyboardArrowDown
import androidx.compose.material.icons.filled.KeyboardArrowUp
import androidx.compose.material.icons.filled.Settings
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.hitit.app.AppBuildConfig
import com.hitit.app.showDebugOptions
import com.hitit.app.ui.viewmodel.HomeViewModel
import hitit.composeapp.generated.resources.*
import org.jetbrains.compose.resources.stringResource
import org.koin.compose.koinInject

@Composable
fun HomeScreen(
    onStartScanning: () -> Unit,
    onOpenDebugSettings: () -> Unit = {},
    viewModel: HomeViewModel = koinInject()
) {
    val uiState by viewModel.uiState.collectAsStateWithLifecycle()
    var instructionsExpanded by remember { mutableStateOf(false) }

    Surface(
        modifier = Modifier.fillMaxSize(),
        color = MaterialTheme.colorScheme.background
    ) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(24.dp),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center
        ) {
            Text(
                text = stringResource(Res.string.app_name),
                style = MaterialTheme.typography.displayLarge,
                color = MaterialTheme.colorScheme.primary
            )

            Spacer(modifier = Modifier.height(8.dp))

            Text(
                text = stringResource(Res.string.app_subtitle),
                style = MaterialTheme.typography.titleMedium,
                color = MaterialTheme.colorScheme.onSurfaceVariant
            )

            Spacer(modifier = Modifier.height(48.dp))

//            // Deezer status card
//            Card(
//                modifier = Modifier.fillMaxWidth(),
//                colors = CardDefaults.cardColors(
//                    containerColor = if (uiState.isDeezerAvailable == true)
//                        MaterialTheme.colorScheme.primaryContainer
//                    else
//                        MaterialTheme.colorScheme.errorContainer
//                )
//            ) {
//                Column(
//                    modifier = Modifier
//                        .fillMaxWidth()
//                        .padding(16.dp),
//                    horizontalAlignment = Alignment.CenterHorizontally
//                ) {
//                    Text(
//                        text = when (uiState.isDeezerAvailable) {
//                            true -> stringResource(Res.string.service_ready, viewModel.serviceName)
//                            false -> stringResource(Res.string.service_not_found, viewModel.serviceName)
//                            null -> stringResource(Res.string.service_checking, viewModel.serviceName)
//                        },
//                        style = MaterialTheme.typography.titleMedium
//                    )
//
//                    if (uiState.isDeezerAvailable == false) {
//                        Spacer(modifier = Modifier.height(8.dp))
//                        Text(
//                            text = stringResource(Res.string.service_install_prompt, viewModel.serviceName),
//                            style = MaterialTheme.typography.bodySmall,
//                            textAlign = TextAlign.Center
//                        )
//                    }
//                }
//            }

            Spacer(modifier = Modifier.height(32.dp))

            // Instructions - Animated expandable button/card
            Card(
                onClick = { instructionsExpanded = !instructionsExpanded },
                modifier = Modifier.fillMaxWidth()
            ) {
                Column(
                    modifier = Modifier
                        .fillMaxWidth()
                        .animateContentSize(
                            animationSpec = spring(
                                dampingRatio = Spring.DampingRatioMediumBouncy,
                                stiffness = Spring.StiffnessLow
                            )
                        )
                        .padding(16.dp)
                ) {
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.SpaceBetween,
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Text(
                            text = stringResource(Res.string.how_to_play),
                            style = MaterialTheme.typography.titleMedium
                        )
                        Icon(
                            imageVector = if (instructionsExpanded)
                                Icons.Default.KeyboardArrowUp
                            else
                                Icons.Default.KeyboardArrowDown,
                            contentDescription = null,
                            tint = MaterialTheme.colorScheme.onSurfaceVariant
                        )
                    }

                    AnimatedVisibility(
                        visible = instructionsExpanded,
                        enter = fadeIn() + expandVertically(
                            animationSpec = spring(
                                dampingRatio = Spring.DampingRatioMediumBouncy,
                                stiffness = Spring.StiffnessLow
                            )
                        ),
                        exit = fadeOut() + shrinkVertically()
                    ) {
                        Column {
                            Spacer(modifier = Modifier.height(12.dp))
                            Text(
                                text = stringResource(Res.string.instruction_step_1) + "\n" +
                                        stringResource(Res.string.instruction_step_2, viewModel.serviceName) + "\n" +
                                        stringResource(Res.string.instruction_step_3) + "\n" +
                                        stringResource(Res.string.instruction_step_4),
                                style = MaterialTheme.typography.bodyMedium
                            )
                        }
                    }
                }
            }

            Spacer(modifier = Modifier.height(48.dp))

            Button(
                onClick = onStartScanning,
                modifier = Modifier
                    .fillMaxWidth()
                    .height(56.dp),
                enabled = uiState.isDeezerAvailable != null
            ) {
                Text(
                    text = stringResource(Res.string.start_game),
                    style = MaterialTheme.typography.titleMedium
                )
            }

            // Debug options - hidden in release builds and release preview
            if (AppBuildConfig.showDebugOptions) {
                Spacer(modifier = Modifier.height(16.dp))

                // Test button for development
                OutlinedButton(
                    onClick = { viewModel.testDeezerPlayback() },
                    modifier = Modifier.fillMaxWidth(),
                    enabled = !uiState.isTestingPlayback
                ) {
                    Text(
                        if (uiState.isTestingPlayback) stringResource(Res.string.opening)
                        else stringResource(Res.string.test_service, viewModel.serviceName)
                    )
                }

                Spacer(modifier = Modifier.height(32.dp))

                // Debug settings button
                TextButton(
                    onClick = onOpenDebugSettings
                ) {
                    Icon(
                        imageVector = Icons.Default.Settings,
                        contentDescription = null,
                        modifier = Modifier.size(18.dp)
                    )
                    Spacer(modifier = Modifier.width(8.dp))
                    Text("Debug Settings")
                }
            }
        }
    }
}
