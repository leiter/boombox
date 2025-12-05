package com.hitit.app.ui.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.hitit.app.ui.components.ScannerFrame
import com.hitit.app.ui.viewmodel.ScannerViewModel
import com.hitit.app.ui.viewmodel.StatusMessage
import hitit.composeapp.generated.resources.*
import org.jetbrains.compose.resources.stringResource
import org.koin.compose.koinInject
import qrscanner.QrScanner

@Composable
fun StatusMessage.toLocalizedString(): String {
    return when (this) {
        is StatusMessage.PointCamera -> stringResource(Res.string.status_point_camera)
        is StatusMessage.Processing -> stringResource(Res.string.status_processing)
        is StatusMessage.ScanError -> stringResource(Res.string.status_scan_error, error)
        is StatusMessage.HitsterCard -> {
            if (title != null && artist != null) {
                stringResource(Res.string.status_hitster_card_with_info, artist, title)
            } else {
                stringResource(Res.string.status_hitster_card, cardId)
            }
        }
        is StatusMessage.FetchingTrack -> stringResource(Res.string.status_fetching_track, cardId)
        is StatusMessage.FlipToPlay -> {
            if (title != null && artist != null) {
                stringResource(Res.string.status_flip_to_play_with_info, artist, title)
            } else {
                stringResource(Res.string.status_flip_to_play)
            }
        }
        is StatusMessage.NowPlaying -> {
            if (title != null && artist != null) {
                stringResource(Res.string.status_playing_with_info, artist, title)
            } else {
                stringResource(Res.string.status_playing, "")
            }
        }
        is StatusMessage.OpeningTrack -> {
            if (title != null && artist != null) {
                stringResource(Res.string.status_opening_track_with_info, artist, title)
            } else {
                stringResource(Res.string.status_opening_track, serviceName)
            }
        }
        is StatusMessage.Playing -> {
            if (title != null && artist != null) {
                stringResource(Res.string.status_playing_with_info, artist, title)
            } else {
                stringResource(Res.string.status_playing, serviceName)
            }
        }
        is StatusMessage.CouldNotOpen -> stringResource(Res.string.status_could_not_open, serviceName)
        is StatusMessage.CardNotFound -> stringResource(Res.string.status_card_not_found, cardId)
        is StatusMessage.SpotifyDetected -> stringResource(Res.string.status_spotify_detected, trackId)
        is StatusMessage.YouTubeDetected -> stringResource(Res.string.status_youtube_detected, videoId)
        is StatusMessage.UrlDetected -> stringResource(Res.string.status_url_detected, url)
        is StatusMessage.UnknownQr -> stringResource(Res.string.status_unknown_qr, content)
    }
}

@Composable
fun ScannerScreen(
    onBackToHome: () -> Unit,
    viewModel: ScannerViewModel = koinInject()
) {
    val uiState by viewModel.uiState.collectAsStateWithLifecycle()

    // Show NowPlayingScreen when playing
    if (uiState.isNowPlaying) {
        val (title, artist, year) = when (val status = uiState.status) {
            is StatusMessage.NowPlaying -> Triple(status.title, status.artist, status.year)
            else -> Triple(null, null, null)
        }
        NowPlayingScreen(
            title = title,
            artist = artist,
            year = year,
            isPlaying = true,
            onPlayPauseClick = { /* TODO: Toggle playback */ },
            onNextCard = { viewModel.resetScanner() },
            onClose = { viewModel.resetScanner() }
        )
        return
    }

    // Show FlipPhoneScreen when waiting for flip
    if (uiState.isWaitingForFlip) {
        val (title, artist) = when (val status = uiState.status) {
            is StatusMessage.FlipToPlay -> status.title to status.artist
            else -> null to null
        }
        FlipPhoneScreen(
            title = title,
            artist = artist,
            onClose = { viewModel.resetScanner() }
        )
        return
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(MaterialTheme.colorScheme.background)
    ) {
        // Top bar
        Surface(
            modifier = Modifier.fillMaxWidth(),
            color = MaterialTheme.colorScheme.primary
        ) {
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(16.dp),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(
                    text = stringResource(Res.string.scan_card),
                    style = MaterialTheme.typography.titleLarge,
                    color = MaterialTheme.colorScheme.onPrimary
                )
                TextButton(onClick = onBackToHome) {
                    Text(stringResource(Res.string.close), color = MaterialTheme.colorScheme.onPrimary)
                }
            }
        }

        // Camera preview
        Box(
            modifier = Modifier
                .weight(1f)
                .fillMaxWidth()
                .padding(16.dp)
        ) {
            QrScanner(
                modifier = Modifier
                    .fillMaxSize()
                    .clip(RoundedCornerShape(16.dp)),
                flashlightOn = uiState.flashlightOn,
                openImagePicker = false,
                imagePickerHandler = { /* Not used */ },
                onFailure = { error ->
                    viewModel.onScanError(error.toString())
                },
                onCompletion = { result ->
                    viewModel.onQrCodeScanned(result)
                }
            )

            // Cyan scanning frame overlay
            ScannerFrame(
                modifier = Modifier.fillMaxSize()
            )

            // Scanning overlay indicator
            if (uiState.isProcessing) {
                Box(
                    modifier = Modifier
                        .fillMaxSize()
                        .background(Color.Black.copy(alpha = 0.5f)),
                    contentAlignment = Alignment.Center
                ) {
                    CircularProgressIndicator(color = Color.White)
                }
            }
        }

        // Status and controls
        Surface(
            modifier = Modifier.fillMaxWidth(),
            color = MaterialTheme.colorScheme.surfaceVariant
        ) {
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(16.dp),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Text(
                    text = uiState.status.toLocalizedString(),
                    style = MaterialTheme.typography.bodyLarge,
                    textAlign = TextAlign.Center
                )

                Spacer(modifier = Modifier.height(16.dp))

                Row(
                    horizontalArrangement = Arrangement.spacedBy(16.dp)
                ) {
                    OutlinedButton(
                        onClick = { viewModel.toggleFlashlight() }
                    ) {
                        Text(if (uiState.flashlightOn) stringResource(Res.string.flash_off) else stringResource(Res.string.flash_on))
                    }

                    Button(
                        onClick = { viewModel.resetScanner() }
                    ) {
                        Text(stringResource(Res.string.scan_again))
                    }
                }

                // DEBUG: Test button to simulate scanning card #00001
                Spacer(modifier = Modifier.height(8.dp))
                TextButton(
                    onClick = { viewModel.onQrCodeScanned("https://hitstergame.com/en/00001") }
                ) {
                    Text("DEBUG: Test Scan", color = Color.Gray)
                }
            }
        }
    }
}
