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
                text = "HitIt",
                style = MaterialTheme.typography.displayLarge,
                color = MaterialTheme.colorScheme.primary
            )

            Spacer(modifier = Modifier.height(8.dp))

            Text(
                text = "Music Quiz Game",
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
                            true -> "${viewModel.serviceName} Ready"
                            false -> "${viewModel.serviceName} Not Found"
                            null -> "Checking ${viewModel.serviceName}..."
                        },
                        style = MaterialTheme.typography.titleMedium
                    )

                    if (uiState.isDeezerAvailable == false) {
                        Spacer(modifier = Modifier.height(8.dp))
                        Text(
                            text = "Please install the ${viewModel.serviceName} app to play music",
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
                        text = "How to Play",
                        style = MaterialTheme.typography.titleMedium
                    )
                    Spacer(modifier = Modifier.height(8.dp))
                    Text(
                        text = "1. Scan a Hitster card QR code\n" +
                                "2. Listen to the song in ${viewModel.serviceName}\n" +
                                "3. Guess the year, artist & title\n" +
                                "4. Check your answer on the card!",
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
                    text = "Scan Card",
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
                    if (uiState.isTestingPlayback) "Opening..."
                    else "Test ${viewModel.serviceName} (Get Lucky)"
                )
            }
        }
    }
}
