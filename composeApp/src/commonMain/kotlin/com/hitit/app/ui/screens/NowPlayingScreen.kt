package com.hitit.app.ui.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.systemBars
import androidx.compose.foundation.layout.windowInsetsPadding
import androidx.compose.foundation.shape.CircleShape
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
    onPlayPauseClick: () -> Unit = {},
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
                    .background(Brush.linearGradient(listOf(Primary, Secondary))),
                contentAlignment = Alignment.Center
            ) {
                Icon(
                    imageVector = Icons.Default.PlayArrow,
                    contentDescription = if (isPlaying) "Playing" else "Play",
                    modifier = Modifier.size(32.dp),
                    tint = Color.White
                )
            }

            Spacer(modifier = Modifier.height(48.dp))

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
