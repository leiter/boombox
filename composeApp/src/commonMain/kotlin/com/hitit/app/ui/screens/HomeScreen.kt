package com.hitit.app.ui.screens

import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.hitit.app.ui.viewmodel.HomeViewModel
import hitit.composeapp.generated.resources.*
import org.jetbrains.compose.resources.stringResource
import org.koin.compose.koinInject

@Composable
fun HomeScreen(
    onStartScanning: () -> Unit,
    viewModel: HomeViewModel = koinInject()
) {
    val uiState by viewModel.uiState.collectAsStateWithLifecycle()

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

            // Deezer status card
            Card(
                modifier = Modifier.fillMaxWidth(),
                colors = CardDefaults.cardColors(
                    containerColor = if (uiState.isDeezerAvailable == true)
                        MaterialTheme.colorScheme.primaryContainer
                    else
                        MaterialTheme.colorScheme.errorContainer
                )
            ) {
                Column(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(16.dp),
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    Text(
                        text = when (uiState.isDeezerAvailable) {
                            true -> stringResource(Res.string.service_ready, viewModel.serviceName)
                            false -> stringResource(Res.string.service_not_found, viewModel.serviceName)
                            null -> stringResource(Res.string.service_checking, viewModel.serviceName)
                        },
                        style = MaterialTheme.typography.titleMedium
                    )

                    if (uiState.isDeezerAvailable == false) {
                        Spacer(modifier = Modifier.height(8.dp))
                        Text(
                            text = stringResource(Res.string.service_install_prompt, viewModel.serviceName),
                            style = MaterialTheme.typography.bodySmall,
                            textAlign = TextAlign.Center
                        )
                    }
                }
            }

            Spacer(modifier = Modifier.height(32.dp))

            // Instructions
            Card(
                modifier = Modifier.fillMaxWidth()
            ) {
                Column(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(16.dp)
                ) {
                    Text(
                        text = stringResource(Res.string.how_to_play),
                        style = MaterialTheme.typography.titleMedium
                    )
                    Spacer(modifier = Modifier.height(8.dp))
                    Text(
                        text = stringResource(Res.string.instruction_step_1) + "\n" +
                                stringResource(Res.string.instruction_step_2, viewModel.serviceName) + "\n" +
                                stringResource(Res.string.instruction_step_3) + "\n" +
                                stringResource(Res.string.instruction_step_4),
                        style = MaterialTheme.typography.bodyMedium
                    )
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
                    text = stringResource(Res.string.scan_card),
                    style = MaterialTheme.typography.titleMedium
                )
            }

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
        }
    }
}
