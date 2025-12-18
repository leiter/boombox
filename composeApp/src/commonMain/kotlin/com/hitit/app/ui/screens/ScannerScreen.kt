package com.hitit.app.ui.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.navigationBars
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.statusBars
import androidx.compose.foundation.layout.windowInsetsPadding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Close
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.DisposableEffect
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.LifecycleEventObserver
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.hitit.app.AppBuildConfig
import com.hitit.app.BuildKonfig
import com.hitit.app.service.AudioPlayer
import com.hitit.app.showDebugOptions
import com.hitit.app.ui.components.PlatformBackHandler
import com.hitit.app.ui.components.ScannerFrame
import com.hitit.app.ui.components.ScannerOverlay
import com.hitit.app.ui.theme.BackgroundDark
import com.hitit.app.ui.theme.Primary
import com.hitit.app.ui.theme.Secondary
import com.hitit.app.ui.theme.SurfaceLight
import com.hitit.app.ui.theme.TextSecondary
import com.hitit.app.ui.viewmodel.ScannerViewModel
import com.hitit.app.ui.viewmodel.StatusMessage
import hitit.composeapp.generated.resources.Res
import hitit.composeapp.generated.resources.close
import hitit.composeapp.generated.resources.flash_off
import hitit.composeapp.generated.resources.flash_on
import hitit.composeapp.generated.resources.status_card_not_found
import hitit.composeapp.generated.resources.status_could_not_open
import hitit.composeapp.generated.resources.status_fetching_track
import hitit.composeapp.generated.resources.status_flip_to_play
import hitit.composeapp.generated.resources.status_flip_to_play_with_info
import hitit.composeapp.generated.resources.status_hitster_card
import hitit.composeapp.generated.resources.status_hitster_card_with_info
import hitit.composeapp.generated.resources.status_opening_track
import hitit.composeapp.generated.resources.status_opening_track_with_info
import hitit.composeapp.generated.resources.status_playing
import hitit.composeapp.generated.resources.status_playing_with_info
import hitit.composeapp.generated.resources.status_point_camera
import hitit.composeapp.generated.resources.status_processing
import hitit.composeapp.generated.resources.status_scan_error
import hitit.composeapp.generated.resources.status_spotify_detected
import hitit.composeapp.generated.resources.status_unknown_qr
import hitit.composeapp.generated.resources.status_url_detected
import hitit.composeapp.generated.resources.status_youtube_detected
import org.jetbrains.compose.resources.stringResource
import org.koin.compose.koinInject
import qrscanner.CameraLens
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
        is StatusMessage.FetchingTrack ->
            if (BuildKonfig.IS_DEBUG)
                stringResource(Res.string.status_fetching_track, cardId)
            else ""
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
    viewModel: ScannerViewModel = koinInject(),
    audioPlayer: AudioPlayer = koinInject()
) {
    val uiState by viewModel.uiState.collectAsStateWithLifecycle()
    val lifecycleOwner = androidx.lifecycle.compose.LocalLifecycleOwner.current

    // Flash state - pass directly to QrScanner, persistence handles restoration
    // QRKit handles the hardware, we just track user preference

    // Handle audio focus changes when returning to DukeStar
    DisposableEffect(lifecycleOwner) {
        val observer = LifecycleEventObserver { _, event ->
            if (event == Lifecycle.Event.ON_RESUME && uiState.isNowPlaying) {
                audioPlayer.stopExternalPlayback()
                viewModel.onAudioFocusReturned()
            }
        }
        lifecycleOwner.lifecycle.addObserver(observer)
        onDispose {
            lifecycleOwner.lifecycle.removeObserver(observer)
        }
    }

    // Handle back press - go to scanner if playing/waiting, otherwise go home
    PlatformBackHandler {
        if (uiState.isNowPlaying || uiState.isWaitingForFlip) {
            // Return to scanner screen
            viewModel.resetScanner()
        } else {
            // Exit scanner, go to home
            viewModel.resetScanner()
            onBackToHome()
        }
    }

    // Use Box to layer screens
    Box(modifier = Modifier.fillMaxSize()) {
        // Base layer: Scanner screen
        Column(
            modifier = Modifier
                .fillMaxSize()
                .background(BackgroundDark)
        ) {
            // Fullscreen camera preview with overlay
            Box(
                modifier = Modifier
                    .weight(1f)
                    .fillMaxWidth()
                    .background(Color.Black),
                contentAlignment = Alignment.Center
            ) {
                // Only render camera when actively scanning (saves battery)
                val isScanning = !uiState.isWaitingForFlip && !uiState.isNowPlaying
                if (isScanning) {
                    QrScanner(
                        modifier = Modifier.fillMaxSize(),
                        cameraLens = CameraLens.Back,
                        flashlightOn = uiState.flashlightOn,
                        openImagePicker = false,
                        customOverlay = { /* Empty - we draw our own overlay */ },
                        imagePickerHandler = { /* Not used */ },
                        onFailure = { error ->
                            viewModel.onScanError(error.toString())
                        },
                        onCompletion = { result ->
                            viewModel.onQrCodeScanned(result)
                        }
                    )
                }

                // Dark overlay with transparent cutout
                ScannerOverlay(
                    modifier = Modifier.fillMaxSize(),
                    scanAreaFraction = 0.75f
                )

                // Cyan scanning frame
                ScannerFrame(
                    modifier = Modifier
                        .fillMaxWidth(0.75f)
                        .aspectRatio(1f)
                )

                // Scanning overlay indicator
                if (uiState.isProcessing) {
                    Box(
                        modifier = Modifier
                            .fillMaxWidth(0.75f)
                            .aspectRatio(1f)
                            .background(Color.Black.copy(alpha = 0.5f)),
                        contentAlignment = Alignment.Center
                    ) {
                        CircularProgressIndicator(color = Color.White)
                    }
                }
            }

            // Status and controls
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .background(SurfaceLight)
                    .windowInsetsPadding(WindowInsets.navigationBars)
                    .padding(24.dp)
            ) {
                Column(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    Text(
                        text = uiState.status.toLocalizedString(),
                        style = MaterialTheme.typography.bodyLarge,
                        textAlign = TextAlign.Center,
                        color = Color.White
                    )

                    Spacer(modifier = Modifier.height(20.dp))

                    // Flash button with clear on/off indication
                    Box(
                        modifier = Modifier
                            .clip(RoundedCornerShape(28.dp))
                            .then(
                                if (uiState.flashlightOn) {
                                    Modifier.background(Brush.horizontalGradient(listOf(Primary, Secondary)))
                                } else {
                                    Modifier
                                        .background(Color.Transparent)
                                        .border(1.dp, Secondary, RoundedCornerShape(28.dp))
                                }
                            )
                            .clickable { viewModel.toggleFlashlight() }
                            .padding(horizontal = 24.dp, vertical = 12.dp),
                        contentAlignment = Alignment.Center
                    ) {
                        Row(
                            verticalAlignment = Alignment.CenterVertically,
                            horizontalArrangement = Arrangement.spacedBy(8.dp)
                        ) {
                            // Flash indicator dot
                            Box(
                                modifier = Modifier
                                    .size(8.dp)
                                    .clip(RoundedCornerShape(4.dp))
                                    .background(if (uiState.flashlightOn) Color.White else Secondary)
                            )
                            Text(
                                text = if (uiState.flashlightOn) stringResource(Res.string.flash_off) else stringResource(Res.string.flash_on),
                                color = if (uiState.flashlightOn) Color.White else Secondary
                            )
                        }
                    }

                    // DEBUG: Test button to simulate scanning card #00001
                    if (AppBuildConfig.showDebugOptions) {
                        Spacer(modifier = Modifier.height(8.dp))
                        TextButton(
                            onClick = { viewModel.onQrCodeScanned("https://hitstergame.com/en/00001") }
                        ) {
                            Text("DEBUG: Test Scan", color = TextSecondary)
                        }
                    }
                }
            }
        } // End of Column (base scanner layer)

        // Floating close button (not contained in a bar)
        IconButton(
            onClick = {
                viewModel.resetScanner()
                onBackToHome()
            },
            modifier = Modifier
                .align(Alignment.TopEnd)
                .windowInsetsPadding(WindowInsets.statusBars)
                .padding(8.dp)
        ) {
            Icon(
                imageVector = Icons.Default.Close,
                contentDescription = stringResource(Res.string.close),
                tint = Color.White
            )
        }

        // Overlay layer: FlipPhoneScreen (shown when waiting for flip)
        if (uiState.isWaitingForFlip) {
            FlipPhoneScreen(
                onClose = { viewModel.resetScanner() },
                isDeezerInstalled = uiState.isDeezerInstalled,
                selectedPlaybackMode = uiState.selectedPlaybackMode,
                onPlaybackModeChanged = { mode -> viewModel.setPlaybackMode(mode) }
            )
        }

        // Overlay layer: NowPlayingScreen (shown when playing)
        if (uiState.isNowPlaying) {
            val (title, artist, year, albumCoverUrl) = when (val status = uiState.status) {
                is StatusMessage.NowPlaying -> listOf(status.title, status.artist, status.year, status.albumCoverUrl)
                else -> listOf(null, null, null, null)
            }
            NowPlayingScreen(
                title = title as String?,
                artist = artist as String?,
                year = year as Int?,
                albumCoverUrl = albumCoverUrl as String?,
                isPlaying = uiState.isAudioPlaying,
                onPlayPauseClick = { viewModel.togglePlayPause() },
                onNextCard = { viewModel.resetScanner() },
                onClose = { viewModel.resetScanner() }
            )
        }
    } // End of Box
}
