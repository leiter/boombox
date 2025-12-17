package com.hitit.app.ui.preview

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Close
import androidx.compose.material.icons.filled.KeyboardArrowDown
import androidx.compose.material.icons.filled.PlayArrow
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

/**
 * Design Proposal 1: Neon Cyber
 * Glassmorphism with neon accents
 */
object NeonCyberColors {
    val Primary = Color(0xFFFF00FF)
    val Secondary = Color(0xFF00FFFF)
    val Accent = Color(0xFFFF6B35)
    val BackgroundStart = Color(0xFF1A0A2E)
    val BackgroundEnd = Color(0xFF0D0221)
    val Surface = Color(0x22FFFFFF)
    val SurfaceBorder = Color(0x44FFFFFF)
    val TextPrimary = Color.White
    val TextSecondary = Color(0xAAFFFFFF)
}

// ==================== SPLASH SCREEN ====================

@Composable
fun NeonCyberSplashScreen(modifier: Modifier = Modifier) {
    Box(
        modifier = modifier
            .fillMaxWidth()
            .height(700.dp)
            .background(Brush.verticalGradient(listOf(NeonCyberColors.BackgroundStart, NeonCyberColors.BackgroundEnd))),
        contentAlignment = Alignment.Center
    ) {
        Column(horizontalAlignment = Alignment.CenterHorizontally) {
            // Simple speaker icon using shapes
            Row(horizontalArrangement = Arrangement.spacedBy(16.dp)) {
                Box(
                    Modifier
                        .size(60.dp)
                        .clip(CircleShape)
                        .border(3.dp, NeonCyberColors.Primary, CircleShape)
                        .background(NeonCyberColors.Primary.copy(alpha = 0.2f))
                )
                Box(
                    Modifier
                        .size(60.dp)
                        .clip(CircleShape)
                        .border(3.dp, NeonCyberColors.Secondary, CircleShape)
                        .background(NeonCyberColors.Secondary.copy(alpha = 0.2f))
                )
            }

            Spacer(Modifier.height(32.dp))

            Text("DukeStar", fontSize = 48.sp, fontWeight = FontWeight.Bold, color = NeonCyberColors.TextPrimary)
            Spacer(Modifier.height(8.dp))
            Text("Music Quiz Game", fontSize = 18.sp, color = NeonCyberColors.Secondary)
        }
    }
}

// ==================== HOME SCREEN ====================

@Composable
fun NeonCyberHomeScreen(modifier: Modifier = Modifier) {
    Box(
        modifier = modifier
            .fillMaxWidth()
            .height(700.dp)
            .background(Brush.verticalGradient(listOf(NeonCyberColors.BackgroundStart, NeonCyberColors.BackgroundEnd)))
    ) {
        Column(
            Modifier.fillMaxSize().padding(24.dp),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center
        ) {
            Text("DukeStar", fontSize = 32.sp, fontWeight = FontWeight.Bold, color = NeonCyberColors.TextPrimary)
            Spacer(Modifier.height(48.dp))

            // Glass card
            Box(
                Modifier
                    .fillMaxWidth()
                    .clip(RoundedCornerShape(16.dp))
                    .background(NeonCyberColors.Surface)
                    .border(1.dp, NeonCyberColors.SurfaceBorder, RoundedCornerShape(16.dp))
                    .padding(16.dp)
            ) {
                Row(Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.SpaceBetween) {
                    Text("How to Play", fontSize = 18.sp, color = NeonCyberColors.TextPrimary)
                    Icon(Icons.Default.KeyboardArrowDown, null, tint = NeonCyberColors.Secondary)
                }
            }

            Spacer(Modifier.height(48.dp))

            // Neon button
            Box(
                Modifier
                    .fillMaxWidth()
                    .height(56.dp)
                    .clip(RoundedCornerShape(28.dp))
                    .background(Brush.horizontalGradient(listOf(NeonCyberColors.Primary, NeonCyberColors.Secondary))),
                contentAlignment = Alignment.Center
            ) {
                Text("Start Game", fontSize = 18.sp, fontWeight = FontWeight.Bold, color = Color.White)
            }
        }
    }
}

// ==================== SCANNER SCREEN ====================

@Composable
fun NeonCyberScannerScreen(modifier: Modifier = Modifier) {
    Column(
        modifier = modifier
            .fillMaxWidth()
            .height(700.dp)
            .background(NeonCyberColors.BackgroundEnd)
    ) {
        // Top bar - solid color instead of gradient
        Box(
            Modifier
                .fillMaxWidth()
                .background(NeonCyberColors.Primary)
                .padding(8.dp)
        ) {
            IconButton({}, Modifier.align(Alignment.TopEnd)) {
                Icon(Icons.Default.Close, "Close", tint = Color.White)
            }
        }

        // Scanner area - fixed height instead of weight
        Box(
            Modifier
                .fillMaxWidth()
                .height(450.dp)
                .background(Color(0xFF1A1A1A)),
            contentAlignment = Alignment.Center
        ) {
            Box(
                Modifier
                    .size(250.dp)
                    .border(3.dp, NeonCyberColors.Primary, RoundedCornerShape(16.dp))
            )
        }

        // Bottom
        Box(
            Modifier
                .fillMaxWidth()
                .background(NeonCyberColors.Surface)
                .padding(16.dp)
        ) {
            Column(Modifier.fillMaxWidth(), horizontalAlignment = Alignment.CenterHorizontally) {
                Text("Point camera at QR code", color = NeonCyberColors.TextPrimary)
                Spacer(Modifier.height(16.dp))
                OutlinedButton({}) { Text("Flash On", color = NeonCyberColors.Secondary) }
            }
        }
    }
}

// ==================== FLIP PHONE SCREEN ====================

@Composable
fun NeonCyberFlipPhoneScreen(modifier: Modifier = Modifier) {
    Box(
        modifier = modifier
            .fillMaxWidth()
            .height(700.dp)
            .background(Brush.verticalGradient(listOf(NeonCyberColors.BackgroundStart, NeonCyberColors.BackgroundEnd)))
    ) {
        IconButton({}, Modifier.align(Alignment.TopEnd).padding(16.dp)) {
            Icon(Icons.Default.Close, "Close", tint = Color.White)
        }

        Column(
            Modifier.fillMaxSize().padding(32.dp),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center
        ) {
            // Simple phone shape
            Box(
                Modifier
                    .width(120.dp)
                    .height(200.dp)
                    .clip(RoundedCornerShape(24.dp))
                    .border(3.dp, NeonCyberColors.Secondary, RoundedCornerShape(24.dp))
            )

            Spacer(Modifier.height(48.dp))
            Text("Flip Phone Face Down", fontSize = 24.sp, fontWeight = FontWeight.Bold, color = NeonCyberColors.TextPrimary, textAlign = TextAlign.Center)
            Spacer(Modifier.height(12.dp))
            Text("Music starts automatically", color = NeonCyberColors.TextSecondary)

            Spacer(Modifier.height(32.dp))
            Row(horizontalArrangement = Arrangement.spacedBy(12.dp)) {
                Box(
                    Modifier
                        .clip(RoundedCornerShape(20.dp))
                        .background(NeonCyberColors.Primary.copy(alpha = 0.2f))
                        .border(1.dp, NeonCyberColors.Primary, RoundedCornerShape(20.dp))
                        .padding(horizontal = 16.dp, vertical = 10.dp)
                ) { Text("Preview", color = NeonCyberColors.Primary) }
                Box(
                    Modifier
                        .clip(RoundedCornerShape(20.dp))
                        .border(1.dp, NeonCyberColors.SurfaceBorder, RoundedCornerShape(20.dp))
                        .padding(horizontal = 16.dp, vertical = 10.dp)
                ) { Text("Deezer", color = NeonCyberColors.TextSecondary) }
            }
        }
    }
}

// ==================== NOW PLAYING SCREEN ====================

@Composable
fun NeonCyberNowPlayingScreen(modifier: Modifier = Modifier) {
    Box(
        modifier = modifier
            .fillMaxWidth()
            .height(700.dp)
            .background(Brush.verticalGradient(listOf(NeonCyberColors.BackgroundStart, NeonCyberColors.BackgroundEnd)))
    ) {
        IconButton({}, Modifier.align(Alignment.TopEnd).padding(16.dp)) {
            Icon(Icons.Default.Close, "Close", tint = Color.White)
        }

        Column(
            Modifier.fillMaxSize().padding(32.dp),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center
        ) {
            // Album art
            Box(
                Modifier
                    .size(180.dp)
                    .clip(RoundedCornerShape(16.dp))
                    .border(3.dp, Brush.linearGradient(listOf(NeonCyberColors.Primary, NeonCyberColors.Secondary)), RoundedCornerShape(16.dp))
                    .background(NeonCyberColors.Primary.copy(alpha = 0.2f)),
                contentAlignment = Alignment.Center
            ) { Text("♪", fontSize = 64.sp, color = Color.White) }

            Spacer(Modifier.height(32.dp))
            Text("Billie Jean", fontSize = 26.sp, fontWeight = FontWeight.Bold, color = NeonCyberColors.TextPrimary)
            Spacer(Modifier.height(8.dp))
            Text("Michael Jackson", fontSize = 18.sp, color = NeonCyberColors.TextSecondary)

            Spacer(Modifier.height(16.dp))
            Box(
                Modifier
                    .clip(RoundedCornerShape(20.dp))
                    .background(NeonCyberColors.Secondary.copy(alpha = 0.2f))
                    .border(1.dp, NeonCyberColors.Secondary, RoundedCornerShape(20.dp))
                    .padding(horizontal = 24.dp, vertical = 8.dp)
            ) { Text("1982", fontSize = 22.sp, fontWeight = FontWeight.Bold, color = NeonCyberColors.Secondary) }

            Spacer(Modifier.height(32.dp))
            Box(
                Modifier.size(64.dp).clip(CircleShape).background(Brush.linearGradient(listOf(NeonCyberColors.Primary, NeonCyberColors.Secondary))),
                contentAlignment = Alignment.Center
            ) { Icon(Icons.Default.PlayArrow, "Play", Modifier.size(32.dp), tint = Color.White) }

            Spacer(Modifier.height(48.dp))
            Box(
                Modifier.fillMaxWidth().height(56.dp).clip(RoundedCornerShape(28.dp)).background(Brush.horizontalGradient(listOf(NeonCyberColors.Primary, NeonCyberColors.Secondary))),
                contentAlignment = Alignment.Center
            ) { Text("Next Card", fontWeight = FontWeight.Bold, color = Color.White) }
        }
    }
}

// ==================== PREVIEWS ====================

@Preview(name = "Neon Cyber - Splash", showBackground = true, widthDp = 400, heightDp = 700)
@Composable
fun NeonCyberSplashPreview() = NeonCyberSplashScreen()

@Preview(name = "Neon Cyber - Home", showBackground = true, widthDp = 400, heightDp = 700)
@Composable
fun NeonCyberHomePreview() = NeonCyberHomeScreen()

@Preview(name = "Neon Cyber - Scanner", showBackground = true, widthDp = 400, heightDp = 700)
@Composable
fun NeonCyberScannerPreview() {
    NeonCyberScannerScreen()
}


@Preview(name = "Neon Cyber - Now Playing", showBackground = true, widthDp = 400, heightDp = 700)
@Composable
fun NeonCyberNowPlayingPreview() = NeonCyberNowPlayingScreen()
