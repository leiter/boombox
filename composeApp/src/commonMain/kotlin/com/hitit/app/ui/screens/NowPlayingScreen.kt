package com.hitit.app.ui.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.systemBars
import androidx.compose.foundation.layout.windowInsetsPadding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Close
import androidx.compose.material.icons.filled.PlayArrow
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import hitit.composeapp.generated.resources.*
import org.jetbrains.compose.resources.stringResource

/**
 * Screen shown while music is playing - displays track info and controls
 */
@Composable
fun NowPlayingScreen(
    title: String?,
    artist: String?,
    year: Int?,
    isPlaying: Boolean = true,
    onPlayPauseClick: () -> Unit = {},
    onNextCard: () -> Unit,
    onClose: () -> Unit
) {
    val primaryColor = Color(0xFFFF6B35) // Orange from theme
    val accentColor = Color(0xFF00D4FF) // Cyan accent

    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(
                Brush.verticalGradient(
                    colors = listOf(
                        Color(0xFF1A1A2E),
                        Color(0xFF16213E),
                        Color(0xFF0F3460)
                    )
                )
            )
            .windowInsetsPadding(WindowInsets.systemBars)
    ) {
        // Close button
        IconButton(
            onClick = onClose,
            modifier = Modifier
                .align(Alignment.TopEnd)
                .padding(16.dp)
        ) {
            Icon(
                imageVector = Icons.Default.Close,
                contentDescription = stringResource(Res.string.close),
                tint = Color.White
            )
        }

        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(horizontal = 32.dp),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center
        ) {
            // Album art placeholder
            Box(
                modifier = Modifier
                    .size(200.dp)
                    .clip(RoundedCornerShape(16.dp))
                    .background(
                        Brush.linearGradient(
                            colors = listOf(primaryColor, primaryColor.copy(alpha = 0.7f))
                        )
                    ),
                contentAlignment = Alignment.Center
            ) {
                // Music note text as placeholder for album art
                Text(
                    text = "♪",
                    fontSize = 64.sp,
                    color = Color.White
                )
            }

            Spacer(modifier = Modifier.height(32.dp))

            // Track title
            Text(
                text = title ?: stringResource(Res.string.unknown_track),
                style = MaterialTheme.typography.headlineMedium,
                fontWeight = FontWeight.Bold,
                color = Color.White,
                textAlign = TextAlign.Center
            )

            Spacer(modifier = Modifier.height(8.dp))

            // Artist
            Text(
                text = artist ?: stringResource(Res.string.unknown_artist),
                style = MaterialTheme.typography.titleMedium,
                color = Color.White.copy(alpha = 0.8f),
                textAlign = TextAlign.Center
            )

            // Year - important for the game!
            if (year != null) {
                Spacer(modifier = Modifier.height(16.dp))
                Surface(
                    shape = RoundedCornerShape(20.dp),
                    color = accentColor.copy(alpha = 0.2f)
                ) {
                    Text(
                        text = year.toString(),
                        style = MaterialTheme.typography.headlineSmall,
                        fontWeight = FontWeight.Bold,
                        color = accentColor,
                        modifier = Modifier.padding(horizontal = 24.dp, vertical = 8.dp)
                    )
                }
            }

            Spacer(modifier = Modifier.height(32.dp))

            // Play/Pause button (using PlayArrow as visual indicator)
            FloatingActionButton(
                onClick = onPlayPauseClick,
                containerColor = primaryColor,
                contentColor = Color.White,
                modifier = Modifier.size(72.dp)
            ) {
                Icon(
                    imageVector = Icons.Default.PlayArrow,
                    contentDescription = if (isPlaying) "Playing" else "Play",
                    modifier = Modifier.size(36.dp)
                )
            }

            Spacer(modifier = Modifier.height(48.dp))

            // Next Card button
            Button(
                onClick = onNextCard,
                colors = ButtonDefaults.buttonColors(
                    containerColor = primaryColor
                ),
                modifier = Modifier
                    .fillMaxWidth()
                    .height(56.dp),
                shape = RoundedCornerShape(28.dp)
            ) {
                Text(
                    text = stringResource(Res.string.next_card),
                    style = MaterialTheme.typography.titleMedium,
                    fontWeight = FontWeight.Bold
                )
            }
        }
    }
}
