package com.hitit.app.ui.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.Canvas
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
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil3.compose.AsyncImage
import com.hitit.app.ui.theme.BackgroundDark
import com.hitit.app.ui.theme.BackgroundLight
import com.hitit.app.ui.theme.Primary
import com.hitit.app.ui.theme.Secondary
import com.hitit.app.ui.theme.TextSecondary
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
    albumCoverUrl: String? = null,
    isPlaying: Boolean = true,
    score: Int = 0,
    total: Int = 0,
    onPlayPauseClick: () -> Unit = {},
    onCorrect: () -> Unit = {},
    onIncorrect: () -> Unit = {},
    onSkip: () -> Unit = {},
    onNextCard: () -> Unit,
    onClose: () -> Unit
) {
    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(
                Brush.verticalGradient(
                    colors = listOf(BackgroundLight, BackgroundDark)
                )
            )
            .windowInsetsPadding(WindowInsets.systemBars)
    ) {
        // Top bar with score and close button
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp)
                .align(Alignment.TopCenter),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            // Score badge
            if (total > 0) {
                Box(
                    modifier = Modifier
                        .clip(RoundedCornerShape(16.dp))
                        .background(Primary.copy(alpha = 0.3f))
                        .border(1.dp, Primary, RoundedCornerShape(16.dp))
                        .padding(horizontal = 16.dp, vertical = 8.dp)
                ) {
                    Text(
                        text = stringResource(Res.string.score_display, score, total),
                        fontSize = 14.sp,
                        fontWeight = FontWeight.Bold,
                        color = Color.White
                    )
                }
            } else {
                Spacer(modifier = Modifier.width(1.dp))
            }

            // Close button
            IconButton(onClick = onClose) {
                Icon(
                    imageVector = Icons.Default.Close,
                    contentDescription = stringResource(Res.string.close),
                    tint = Color.White
                )
            }
        }

        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(horizontal = 32.dp),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center
        ) {
            // Album art with gradient border
            Box(
                modifier = Modifier
                    .size(180.dp)
                    .clip(RoundedCornerShape(16.dp))
                    .border(
                        width = 3.dp,
                        brush = Brush.linearGradient(listOf(Primary, Secondary)),
                        shape = RoundedCornerShape(16.dp)
                    )
                    .background(Primary.copy(alpha = 0.2f)),
                contentAlignment = Alignment.Center
            ) {
                if (albumCoverUrl != null) {
                    AsyncImage(
                        model = albumCoverUrl,
                        contentDescription = "Album cover",
                        modifier = Modifier.fillMaxSize(),
                        contentScale = ContentScale.Crop
                    )
                } else {
                    // Music note as placeholder when no album art
                    Text(
                        text = "♪",
                        fontSize = 64.sp,
                        color = Color.White
                    )
                }
            }

            Spacer(modifier = Modifier.height(32.dp))

            // Track title
            Text(
                text = title ?: stringResource(Res.string.unknown_track),
                fontSize = 26.sp,
                fontWeight = FontWeight.Bold,
                color = Color.White,
                textAlign = TextAlign.Center
            )

            Spacer(modifier = Modifier.height(8.dp))

            // Artist
            Text(
                text = artist ?: stringResource(Res.string.unknown_artist),
                fontSize = 18.sp,
                color = TextSecondary,
                textAlign = TextAlign.Center
            )

            // Year - important for the game!
            if (year != null) {
                Spacer(modifier = Modifier.height(16.dp))
                Box(
                    modifier = Modifier
                        .clip(RoundedCornerShape(20.dp))
                        .background(Secondary.copy(alpha = 0.2f))
                        .border(1.dp, Secondary, RoundedCornerShape(20.dp))
                        .padding(horizontal = 24.dp, vertical = 8.dp)
                ) {
                    Text(
                        text = year.toString(),
                        fontSize = 22.sp,
                        fontWeight = FontWeight.Bold,
                        color = Secondary
                    )
                }
            }

            Spacer(modifier = Modifier.height(32.dp))

            // Play/Pause button with gradient
            Box(
                modifier = Modifier
                    .size(64.dp)
                    .clip(CircleShape)
                    .background(Brush.linearGradient(listOf(Primary, Secondary)))
                    .clickable { onPlayPauseClick() },
                contentAlignment = Alignment.Center
            ) {
                if (isPlaying) {
                    // Custom pause icon (two vertical bars)
                    Canvas(modifier = Modifier.size(32.dp)) {
                        val barWidth = size.width * 0.25f
                        val barHeight = size.height * 0.7f
                        val gap = size.width * 0.15f
                        val startY = (size.height - barHeight) / 2
                        val leftBarX = (size.width - barWidth * 2 - gap) / 2
                        val rightBarX = leftBarX + barWidth + gap

                        drawRoundRect(
                            color = Color.White,
                            topLeft = androidx.compose.ui.geometry.Offset(leftBarX, startY),
                            size = androidx.compose.ui.geometry.Size(barWidth, barHeight),
                            cornerRadius = androidx.compose.ui.geometry.CornerRadius(4f, 4f)
                        )
                        drawRoundRect(
                            color = Color.White,
                            topLeft = androidx.compose.ui.geometry.Offset(rightBarX, startY),
                            size = androidx.compose.ui.geometry.Size(barWidth, barHeight),
                            cornerRadius = androidx.compose.ui.geometry.CornerRadius(4f, 4f)
                        )
                    }
                } else {
                    Icon(
                        imageVector = Icons.Default.PlayArrow,
                        contentDescription = "Play",
                        modifier = Modifier.size(32.dp),
                        tint = Color.White
                    )
                }
            }

            Spacer(modifier = Modifier.height(32.dp))

            // Result buttons - Correct / Incorrect / Skip
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.spacedBy(12.dp)
            ) {
                // Correct button - green
                Box(
                    modifier = Modifier
                        .weight(1f)
                        .height(48.dp)
                        .clip(RoundedCornerShape(24.dp))
                        .background(Color(0xFF4CAF50))
                        .clickable { onCorrect() },
                    contentAlignment = Alignment.Center
                ) {
                    Text(
                        text = stringResource(Res.string.correct),
                        fontWeight = FontWeight.Bold,
                        color = Color.White,
                        fontSize = 14.sp
                    )
                }

                // Incorrect button - red
                Box(
                    modifier = Modifier
                        .weight(1f)
                        .height(48.dp)
                        .clip(RoundedCornerShape(24.dp))
                        .background(Color(0xFFF44336))
                        .clickable { onIncorrect() },
                    contentAlignment = Alignment.Center
                ) {
                    Text(
                        text = stringResource(Res.string.incorrect),
                        fontWeight = FontWeight.Bold,
                        color = Color.White,
                        fontSize = 14.sp
                    )
                }

                // Skip button - gray
                Box(
                    modifier = Modifier
                        .weight(1f)
                        .height(48.dp)
                        .clip(RoundedCornerShape(24.dp))
                        .background(Color(0xFF757575))
                        .clickable { onSkip() },
                    contentAlignment = Alignment.Center
                ) {
                    Text(
                        text = stringResource(Res.string.skip),
                        fontWeight = FontWeight.Bold,
                        color = Color.White,
                        fontSize = 14.sp
                    )
                }
            }

            Spacer(modifier = Modifier.height(24.dp))

            // Next Card button - gradient pill
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(56.dp)
                    .clip(RoundedCornerShape(28.dp))
                    .background(Brush.horizontalGradient(listOf(Primary, Secondary))),
                contentAlignment = Alignment.Center
            ) {
                TextButton(onClick = onNextCard) {
                    Text(
                        text = stringResource(Res.string.next_card),
                        fontWeight = FontWeight.Bold,
                        color = Color.White
                    )
                }
            }
        }
    }
}
