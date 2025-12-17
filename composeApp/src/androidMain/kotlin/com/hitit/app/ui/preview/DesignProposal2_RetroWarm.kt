package com.hitit.app.ui.preview

import androidx.compose.foundation.Canvas
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
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.geometry.CornerRadius
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.StrokeCap
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

/**
 * Design Proposal 2: Retro Warm
 *
 * Visual Style: Neumorphism with soft shadows
 * - Soft extruded buttons and cards
 * - Warm, cozy color temperature
 * - Subtle inner/outer shadows for depth
 *
 * Based on icon V28 (Red retro speakers)
 */
object RetroWarmColors {
    val Primary = Color(0xFFC62828)        // Deep Red
    val PrimaryDark = Color(0xFFB71C1C)
    val PrimaryLight = Color(0xFFE53935)
    val Secondary = Color(0xFFFFD700)      // Gold
    val Accent = Color(0xFFFF8C00)         // Warm Orange

    val BackgroundStart = Color(0xFF2D2D2D)
    val BackgroundMid = Color(0xFF252525)
    val BackgroundEnd = Color(0xFF1A1A1A)

    val Surface = Color(0xFF3D3D3D)
    val SurfaceLight = Color(0xFF4A4A4A)
    val SurfaceDark = Color(0xFF2A2A2A)

    val TextPrimary = Color(0xFFFAFAFA)
    val TextSecondary = Color(0xAAFAFAFA)

    val ShadowLight = Color(0x44FFFFFF)
    val ShadowDark = Color(0x88000000)

    val GradientColors = listOf(BackgroundStart, BackgroundMid, BackgroundEnd)
}

// ==================== NEUMORPHIC COMPONENTS ====================

@Composable
private fun NeumorphicCard(
    modifier: Modifier = Modifier,
    elevation: Dp = 8.dp,
    content: @Composable () -> Unit
) {
    Box(
        modifier = modifier
            .shadow(
                elevation = elevation,
                shape = RoundedCornerShape(16.dp),
                ambientColor = RetroWarmColors.ShadowDark,
                spotColor = RetroWarmColors.ShadowDark
            )
            .clip(RoundedCornerShape(16.dp))
            .background(RetroWarmColors.Surface)
    ) {
        content()
    }
}

@Composable
private fun NeumorphicButton(
    text: String,
    onClick: () -> Unit,
    modifier: Modifier = Modifier
) {
    Box(
        modifier = modifier
            .height(56.dp)
            .shadow(
                elevation = 8.dp,
                shape = RoundedCornerShape(28.dp),
                ambientColor = RetroWarmColors.ShadowDark,
                spotColor = RetroWarmColors.ShadowDark
            )
            .clip(RoundedCornerShape(28.dp))
            .background(
                Brush.verticalGradient(
                    colors = listOf(RetroWarmColors.Primary, RetroWarmColors.PrimaryDark)
                )
            ),
        contentAlignment = Alignment.Center
    ) {
        Text(
            text = text,
            fontSize = 18.sp,
            fontWeight = FontWeight.Bold,
            color = RetroWarmColors.TextPrimary
        )
    }
}

// ==================== SPLASH SCREEN ====================

@Composable
fun RetroWarmSplashScreen(modifier: Modifier = Modifier) {
    Box(
        modifier = modifier
            .fillMaxWidth()
            .height(700.dp)
            .background(Brush.verticalGradient(RetroWarmColors.GradientColors)),
        contentAlignment = Alignment.Center
    ) {
        // Gold burst rays in background
        Canvas(modifier = Modifier.fillMaxSize()) {
            val centerX = size.width / 2
            val centerY = size.height / 2

            // Draw gold rays
            for (i in 0 until 12) {
                val angle = (i * 30) * (Math.PI / 180).toFloat()
                val startRadius = 100.dp.toPx()
                val endRadius = 250.dp.toPx()

                drawLine(
                    color = RetroWarmColors.Secondary.copy(alpha = 0.3f),
                    start = Offset(
                        centerX + startRadius * kotlin.math.cos(angle),
                        centerY + startRadius * kotlin.math.sin(angle)
                    ),
                    end = Offset(
                        centerX + endRadius * kotlin.math.cos(angle),
                        centerY + endRadius * kotlin.math.sin(angle)
                    ),
                    strokeWidth = 20.dp.toPx(),
                    cap = StrokeCap.Round
                )
            }
        }

        Column(
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            // Retro speaker icon
            RetroSpeakerIcon(modifier = Modifier.size(180.dp))

            Spacer(modifier = Modifier.height(32.dp))

            // App name
            Text(
                text = "DukeStar",
                fontSize = 52.sp,
                fontWeight = FontWeight.Bold,
                color = RetroWarmColors.TextPrimary
            )

            Spacer(modifier = Modifier.height(8.dp))

            Text(
                text = "Music Quiz Game",
                fontSize = 18.sp,
                color = RetroWarmColors.Secondary
            )
        }
    }
}

@Composable
private fun RetroSpeakerIcon(modifier: Modifier = Modifier) {
    Canvas(modifier = modifier) {
        val centerX = size.width / 2
        val centerY = size.height / 2
        val speakerWidth = size.width * 0.35f
        val speakerHeight = size.height * 0.6f

        // Left speaker cabinet
        val leftX = centerX - speakerWidth * 1.1f

        // Cabinet shadow
        drawRoundRect(
            color = RetroWarmColors.ShadowDark,
            topLeft = Offset(leftX + 4, centerY - speakerHeight / 2 + 4),
            size = Size(speakerWidth, speakerHeight),
            cornerRadius = CornerRadius(8.dp.toPx())
        )

        // Cabinet body
        drawRoundRect(
            color = RetroWarmColors.Primary,
            topLeft = Offset(leftX, centerY - speakerHeight / 2),
            size = Size(speakerWidth, speakerHeight),
            cornerRadius = CornerRadius(8.dp.toPx())
        )

        // Gold trim
        drawRoundRect(
            color = RetroWarmColors.Secondary,
            topLeft = Offset(leftX + 4, centerY - speakerHeight / 2 + 4),
            size = Size(speakerWidth - 8, speakerHeight - 8),
            cornerRadius = CornerRadius(6.dp.toPx()),
            style = Stroke(width = 2.dp.toPx())
        )

        // Upper speaker (tweeter)
        drawCircle(
            color = RetroWarmColors.Secondary,
            radius = speakerWidth * 0.3f,
            center = Offset(leftX + speakerWidth / 2, centerY - speakerHeight * 0.2f)
        )
        drawCircle(
            color = Color(0xFF333333),
            radius = speakerWidth * 0.22f,
            center = Offset(leftX + speakerWidth / 2, centerY - speakerHeight * 0.2f)
        )
        drawCircle(
            color = RetroWarmColors.Secondary,
            radius = speakerWidth * 0.08f,
            center = Offset(leftX + speakerWidth / 2, centerY - speakerHeight * 0.2f)
        )

        // Lower speaker (woofer)
        drawCircle(
            color = RetroWarmColors.Secondary,
            radius = speakerWidth * 0.4f,
            center = Offset(leftX + speakerWidth / 2, centerY + speakerHeight * 0.15f)
        )
        drawCircle(
            color = Color(0xFF333333),
            radius = speakerWidth * 0.3f,
            center = Offset(leftX + speakerWidth / 2, centerY + speakerHeight * 0.15f)
        )
        drawCircle(
            color = Color(0xFF262626),
            radius = speakerWidth * 0.18f,
            center = Offset(leftX + speakerWidth / 2, centerY + speakerHeight * 0.15f)
        )
        drawCircle(
            color = RetroWarmColors.Secondary,
            radius = speakerWidth * 0.08f,
            center = Offset(leftX + speakerWidth / 2, centerY + speakerHeight * 0.15f)
        )

        // Right speaker cabinet
        val rightX = centerX + speakerWidth * 0.1f

        // Cabinet shadow
        drawRoundRect(
            color = RetroWarmColors.ShadowDark,
            topLeft = Offset(rightX + 4, centerY - speakerHeight / 2 + 4),
            size = Size(speakerWidth, speakerHeight),
            cornerRadius = CornerRadius(8.dp.toPx())
        )

        // Cabinet body
        drawRoundRect(
            color = RetroWarmColors.Primary,
            topLeft = Offset(rightX, centerY - speakerHeight / 2),
            size = Size(speakerWidth, speakerHeight),
            cornerRadius = CornerRadius(8.dp.toPx())
        )

        // Gold trim
        drawRoundRect(
            color = RetroWarmColors.Secondary,
            topLeft = Offset(rightX + 4, centerY - speakerHeight / 2 + 4),
            size = Size(speakerWidth - 8, speakerHeight - 8),
            cornerRadius = CornerRadius(6.dp.toPx()),
            style = Stroke(width = 2.dp.toPx())
        )

        // Upper speaker
        drawCircle(
            color = RetroWarmColors.Secondary,
            radius = speakerWidth * 0.3f,
            center = Offset(rightX + speakerWidth / 2, centerY - speakerHeight * 0.2f)
        )
        drawCircle(
            color = Color(0xFF333333),
            radius = speakerWidth * 0.22f,
            center = Offset(rightX + speakerWidth / 2, centerY - speakerHeight * 0.2f)
        )
        drawCircle(
            color = RetroWarmColors.Secondary,
            radius = speakerWidth * 0.08f,
            center = Offset(rightX + speakerWidth / 2, centerY - speakerHeight * 0.2f)
        )

        // Lower speaker
        drawCircle(
            color = RetroWarmColors.Secondary,
            radius = speakerWidth * 0.4f,
            center = Offset(rightX + speakerWidth / 2, centerY + speakerHeight * 0.15f)
        )
        drawCircle(
            color = Color(0xFF333333),
            radius = speakerWidth * 0.3f,
            center = Offset(rightX + speakerWidth / 2, centerY + speakerHeight * 0.15f)
        )
        drawCircle(
            color = Color(0xFF262626),
            radius = speakerWidth * 0.18f,
            center = Offset(rightX + speakerWidth / 2, centerY + speakerHeight * 0.15f)
        )
        drawCircle(
            color = RetroWarmColors.Secondary,
            radius = speakerWidth * 0.08f,
            center = Offset(rightX + speakerWidth / 2, centerY + speakerHeight * 0.15f)
        )
    }
}

// ==================== HOME SCREEN ====================

@Composable
fun RetroWarmHomeScreen(modifier: Modifier = Modifier) {
    Box(
        modifier = modifier
            .fillMaxWidth()
            .height(700.dp)
            .background(Brush.verticalGradient(RetroWarmColors.GradientColors))
    ) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(24.dp),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center
        ) {
            // Logo
            RetroSpeakerIcon(modifier = Modifier.size(100.dp))

            Spacer(modifier = Modifier.height(8.dp))

            Text(
                text = "DukeStar",
                fontSize = 36.sp,
                fontWeight = FontWeight.Bold,
                color = RetroWarmColors.TextPrimary
            )

            Spacer(modifier = Modifier.height(4.dp))

            Text(
                text = "Music Quiz Game",
                fontSize = 16.sp,
                color = RetroWarmColors.TextSecondary
            )

            Spacer(modifier = Modifier.height(48.dp))

            // Neumorphic card for instructions
            NeumorphicCard(
                modifier = Modifier.fillMaxWidth()
            ) {
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(16.dp),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Text(
                        text = "How to Play",
                        fontSize = 18.sp,
                        fontWeight = FontWeight.Medium,
                        color = RetroWarmColors.TextPrimary
                    )
                    Icon(
                        imageVector = Icons.Default.KeyboardArrowDown,
                        contentDescription = null,
                        tint = RetroWarmColors.Secondary
                    )
                }
            }

            Spacer(modifier = Modifier.height(48.dp))

            // Neumorphic button
            NeumorphicButton(
                text = "Start Game",
                onClick = {},
                modifier = Modifier.fillMaxWidth()
            )
        }
    }
}

// ==================== SCANNER SCREEN ====================

@Composable
fun RetroWarmScannerScreen(modifier: Modifier = Modifier) {
    Column(
        modifier = modifier
            .fillMaxWidth()
            .height(700.dp)
            .background(RetroWarmColors.BackgroundEnd)
    ) {
        // Top bar with gradient
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .background(
                    Brush.verticalGradient(
                        colors = listOf(RetroWarmColors.Primary, RetroWarmColors.PrimaryDark)
                    )
                )
                .padding(8.dp)
        ) {
            IconButton(
                onClick = {},
                modifier = Modifier.align(Alignment.TopEnd)
            ) {
                Icon(
                    imageVector = Icons.Default.Close,
                    contentDescription = "Close",
                    tint = Color.White
                )
            }
        }

        // Scanner area
        Box(
            modifier = Modifier
                .weight(1f)
                .fillMaxWidth()
                .background(Color(0xFF1A1A1A)),
            contentAlignment = Alignment.Center
        ) {
            // Simulated camera background
            Box(
                modifier = Modifier
                    .fillMaxSize()
                    .background(Color(0xFF252525))
            )

            // Gold/warm scanning frame
            RetroScannerFrame(
                modifier = Modifier
                    .fillMaxWidth(0.75f)
                    .aspectRatio(1f)
            )
        }

        // Bottom controls
        NeumorphicCard(
            modifier = Modifier.fillMaxWidth(),
            elevation = 0.dp
        ) {
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .background(RetroWarmColors.Surface)
                    .padding(16.dp),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Text(
                    text = "Point camera at QR code",
                    fontSize = 16.sp,
                    color = RetroWarmColors.TextPrimary
                )

                Spacer(modifier = Modifier.height(16.dp))

                OutlinedButton(
                    onClick = {},
                    colors = ButtonDefaults.outlinedButtonColors(
                        contentColor = RetroWarmColors.Secondary
                    ),
                    border = ButtonDefaults.outlinedButtonBorder.copy(
                        brush = Brush.horizontalGradient(
                            colors = listOf(RetroWarmColors.Secondary, RetroWarmColors.Accent)
                        )
                    )
                ) {
                    Text("Flash On")
                }
            }
        }
    }
}

@Composable
private fun RetroScannerFrame(modifier: Modifier = Modifier) {
    Canvas(modifier = modifier) {
        val strokeWidth = 6.dp.toPx()
        val cornerLength = 50.dp.toPx()
        val cornerRadius = 12.dp.toPx()

        // Draw thick gold corners with warm shadow effect
        val goldColor = RetroWarmColors.Secondary
        val shadowColor = RetroWarmColors.Accent

        // Top-left corner
        drawLine(
            color = shadowColor,
            start = Offset(2f, cornerRadius + 2),
            end = Offset(2f, cornerLength + 2),
            strokeWidth = strokeWidth,
            cap = StrokeCap.Round
        )
        drawLine(
            color = goldColor,
            start = Offset(0f, cornerRadius),
            end = Offset(0f, cornerLength),
            strokeWidth = strokeWidth,
            cap = StrokeCap.Round
        )
        drawLine(
            color = shadowColor,
            start = Offset(cornerRadius + 2, 2f),
            end = Offset(cornerLength + 2, 2f),
            strokeWidth = strokeWidth,
            cap = StrokeCap.Round
        )
        drawLine(
            color = goldColor,
            start = Offset(cornerRadius, 0f),
            end = Offset(cornerLength, 0f),
            strokeWidth = strokeWidth,
            cap = StrokeCap.Round
        )

        // Top-right corner
        drawLine(
            color = goldColor,
            start = Offset(size.width, cornerRadius),
            end = Offset(size.width, cornerLength),
            strokeWidth = strokeWidth,
            cap = StrokeCap.Round
        )
        drawLine(
            color = goldColor,
            start = Offset(size.width - cornerRadius, 0f),
            end = Offset(size.width - cornerLength, 0f),
            strokeWidth = strokeWidth,
            cap = StrokeCap.Round
        )

        // Bottom-left corner
        drawLine(
            color = goldColor,
            start = Offset(0f, size.height - cornerRadius),
            end = Offset(0f, size.height - cornerLength),
            strokeWidth = strokeWidth,
            cap = StrokeCap.Round
        )
        drawLine(
            color = goldColor,
            start = Offset(cornerRadius, size.height),
            end = Offset(cornerLength, size.height),
            strokeWidth = strokeWidth,
            cap = StrokeCap.Round
        )

        // Bottom-right corner
        drawLine(
            color = goldColor,
            start = Offset(size.width, size.height - cornerRadius),
            end = Offset(size.width, size.height - cornerLength),
            strokeWidth = strokeWidth,
            cap = StrokeCap.Round
        )
        drawLine(
            color = goldColor,
            start = Offset(size.width - cornerRadius, size.height),
            end = Offset(size.width - cornerLength, size.height),
            strokeWidth = strokeWidth,
            cap = StrokeCap.Round
        )
    }
}

// ==================== FLIP PHONE SCREEN ====================

@Composable
fun RetroWarmFlipPhoneScreen(modifier: Modifier = Modifier) {
    Box(
        modifier = modifier
            .fillMaxWidth()
            .height(700.dp)
            .background(Brush.verticalGradient(RetroWarmColors.GradientColors))
    ) {
        // Close button
        IconButton(
            onClick = {},
            modifier = Modifier
                .align(Alignment.TopEnd)
                .padding(16.dp)
        ) {
            Icon(
                imageVector = Icons.Default.Close,
                contentDescription = "Close",
                tint = Color.White
            )
        }

        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(32.dp),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center
        ) {
            // Retro phone illustration
            RetroPhoneIllustration(
                modifier = Modifier.size(200.dp, 320.dp)
            )

            Spacer(modifier = Modifier.height(48.dp))

            Text(
                text = "Flip Phone Face Down",
                fontSize = 28.sp,
                fontWeight = FontWeight.Bold,
                color = RetroWarmColors.TextPrimary,
                textAlign = TextAlign.Center
            )

            Spacer(modifier = Modifier.height(12.dp))

            Text(
                text = "Music will start playing automatically",
                fontSize = 16.sp,
                color = RetroWarmColors.TextSecondary,
                textAlign = TextAlign.Center
            )

            Spacer(modifier = Modifier.height(32.dp))

            // Mode selection chips
            Row(
                horizontalArrangement = Arrangement.spacedBy(12.dp)
            ) {
                RetroChip(text = "Preview", selected = true)
                RetroChip(text = "Deezer", selected = false)
            }
        }
    }
}

@Composable
private fun RetroPhoneIllustration(modifier: Modifier = Modifier) {
    Canvas(modifier = modifier) {
        val strokeWidth = 5.dp.toPx()
        val cornerRadius = 24.dp.toPx()
        val phoneWidth = size.width * 0.7f
        val phoneHeight = size.height * 0.85f
        val phoneLeft = (size.width - phoneWidth) / 2
        val phoneTop = (size.height - phoneHeight) / 2

        // Shadow
        drawRoundRect(
            color = RetroWarmColors.ShadowDark,
            topLeft = Offset(phoneLeft + 6, phoneTop + 6),
            size = Size(phoneWidth, phoneHeight),
            cornerRadius = CornerRadius(cornerRadius)
        )

        // Phone body fill
        drawRoundRect(
            color = RetroWarmColors.Surface,
            topLeft = Offset(phoneLeft, phoneTop),
            size = Size(phoneWidth, phoneHeight),
            cornerRadius = CornerRadius(cornerRadius)
        )

        // Phone body outline
        drawRoundRect(
            color = RetroWarmColors.Secondary,
            topLeft = Offset(phoneLeft, phoneTop),
            size = Size(phoneWidth, phoneHeight),
            cornerRadius = CornerRadius(cornerRadius),
            style = Stroke(width = strokeWidth)
        )

        // Notch
        val notchWidth = phoneWidth * 0.3f
        val notchHeight = 8.dp.toPx()
        val notchLeft = (size.width - notchWidth) / 2
        val notchTop = phoneTop + 16.dp.toPx()

        drawRoundRect(
            color = RetroWarmColors.Secondary,
            topLeft = Offset(notchLeft, notchTop),
            size = Size(notchWidth, notchHeight),
            cornerRadius = CornerRadius(notchHeight / 2)
        )

        // Screen area
        val screenPadding = 12.dp.toPx()
        drawRoundRect(
            color = RetroWarmColors.SurfaceDark,
            topLeft = Offset(phoneLeft + screenPadding, phoneTop + 36.dp.toPx()),
            size = Size(phoneWidth - screenPadding * 2, phoneHeight - 52.dp.toPx()),
            cornerRadius = CornerRadius(16.dp.toPx())
        )
    }
}

@Composable
private fun RetroChip(
    text: String,
    selected: Boolean
) {
    val backgroundColor = if (selected) RetroWarmColors.Primary else RetroWarmColors.Surface
    val textColor = if (selected) RetroWarmColors.TextPrimary else RetroWarmColors.TextSecondary

    Box(
        modifier = Modifier
            .shadow(
                elevation = 4.dp,
                shape = RoundedCornerShape(20.dp)
            )
            .clip(RoundedCornerShape(20.dp))
            .background(backgroundColor)
            .then(
                if (selected) {
                    Modifier.border(
                        width = 2.dp,
                        color = RetroWarmColors.Secondary,
                        shape = RoundedCornerShape(20.dp)
                    )
                } else Modifier
            )
            .padding(horizontal = 20.dp, vertical = 12.dp)
    ) {
        Text(
            text = text,
            fontSize = 14.sp,
            fontWeight = if (selected) FontWeight.Bold else FontWeight.Normal,
            color = textColor
        )
    }
}

// ==================== NOW PLAYING SCREEN ====================

@Composable
fun RetroWarmNowPlayingScreen(modifier: Modifier = Modifier) {
    Box(
        modifier = modifier
            .fillMaxWidth()
            .height(700.dp)
            .background(Brush.verticalGradient(RetroWarmColors.GradientColors))
    ) {
        // Close button
        IconButton(
            onClick = {},
            modifier = Modifier
                .align(Alignment.TopEnd)
                .padding(16.dp)
        ) {
            Icon(
                imageVector = Icons.Default.Close,
                contentDescription = "Close",
                tint = Color.White
            )
        }

        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(32.dp),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center
        ) {
            // Album art with neumorphic frame
            Box(
                modifier = Modifier
                    .size(200.dp)
                    .shadow(
                        elevation = 12.dp,
                        shape = RoundedCornerShape(16.dp)
                    )
                    .clip(RoundedCornerShape(16.dp))
                    .background(RetroWarmColors.Surface)
                    .border(
                        width = 3.dp,
                        color = RetroWarmColors.Secondary,
                        shape = RoundedCornerShape(16.dp)
                    ),
                contentAlignment = Alignment.Center
            ) {
                // Vinyl record effect
                Canvas(modifier = Modifier.size(160.dp)) {
                    drawCircle(
                        color = Color(0xFF1A1A1A),
                        radius = size.width / 2
                    )
                    drawCircle(
                        color = RetroWarmColors.Primary,
                        radius = size.width / 2 - 4.dp.toPx(),
                        style = Stroke(width = 2.dp.toPx())
                    )
                    drawCircle(
                        color = Color(0xFF2A2A2A),
                        radius = size.width / 4
                    )
                    drawCircle(
                        color = RetroWarmColors.Secondary,
                        radius = 20.dp.toPx()
                    )
                    drawCircle(
                        color = Color(0xFF1A1A1A),
                        radius = 8.dp.toPx()
                    )
                }
            }

            Spacer(modifier = Modifier.height(32.dp))

            // Track title
            Text(
                text = "Bohemian Rhapsody",
                fontSize = 26.sp,
                fontWeight = FontWeight.Bold,
                color = RetroWarmColors.TextPrimary,
                textAlign = TextAlign.Center
            )

            Spacer(modifier = Modifier.height(8.dp))

            // Artist
            Text(
                text = "Queen",
                fontSize = 18.sp,
                color = RetroWarmColors.TextSecondary,
                textAlign = TextAlign.Center
            )

            Spacer(modifier = Modifier.height(16.dp))

            // Year badge
            Box(
                modifier = Modifier
                    .shadow(4.dp, RoundedCornerShape(20.dp))
                    .clip(RoundedCornerShape(20.dp))
                    .background(RetroWarmColors.Surface)
                    .border(
                        width = 2.dp,
                        color = RetroWarmColors.Secondary,
                        shape = RoundedCornerShape(20.dp)
                    )
                    .padding(horizontal = 24.dp, vertical = 8.dp)
            ) {
                Text(
                    text = "1975",
                    fontSize = 24.sp,
                    fontWeight = FontWeight.Bold,
                    color = RetroWarmColors.Secondary
                )
            }

            Spacer(modifier = Modifier.height(32.dp))

            // Play button
            Box(
                modifier = Modifier
                    .size(72.dp)
                    .shadow(8.dp, CircleShape)
                    .clip(CircleShape)
                    .background(
                        Brush.radialGradient(
                            colors = listOf(RetroWarmColors.Primary, RetroWarmColors.PrimaryDark)
                        )
                    )
                    .border(
                        width = 3.dp,
                        color = RetroWarmColors.Secondary,
                        shape = CircleShape
                    ),
                contentAlignment = Alignment.Center
            ) {
                Icon(
                    imageVector = Icons.Default.PlayArrow,
                    contentDescription = "Playing",
                    modifier = Modifier.size(36.dp),
                    tint = Color.White
                )
            }

            Spacer(modifier = Modifier.height(48.dp))

            // Next card button
            NeumorphicButton(
                text = "Next Card",
                onClick = {},
                modifier = Modifier.fillMaxWidth()
            )
        }
    }
}

// ==================== PREVIEWS ====================

//@Preview(
//    name = "Retro Warm - All Screens",
//    showBackground = true,
//    widthDp = 400,
//    heightDp = 3600
//)
//@Composable
//fun RetroWarmFullPreview() {
//    Column {
//        Text(
//            text = "PROPOSAL 2: RETRO WARM",
//            modifier = Modifier
//                .fillMaxWidth()
//                .background(Color.Black)
//                .padding(16.dp),
//            fontSize = 20.sp,
//            fontWeight = FontWeight.Bold,
//            color = RetroWarmColors.Secondary,
//            textAlign = TextAlign.Center
//        )
//        RetroWarmSplashScreen()
//        Spacer(modifier = Modifier.height(16.dp).background(Color.Black))
//        RetroWarmHomeScreen()
//        Spacer(modifier = Modifier.height(16.dp).background(Color.Black))
//        RetroWarmScannerScreen()
//        Spacer(modifier = Modifier.height(16.dp).background(Color.Black))
//        RetroWarmFlipPhoneScreen()
//        Spacer(modifier = Modifier.height(16.dp).background(Color.Black))
//        RetroWarmNowPlayingScreen()
//    }
//}
//
@Preview(name = "Retro Warm - Splash", showBackground = true, widthDp = 400, heightDp = 700)
@Composable
fun RetroWarmSplashPreview() {
    RetroWarmSplashScreen()
}

@Preview(name = "Retro Warm - Home", showBackground = true, widthDp = 400, heightDp = 700)
@Composable
fun RetroWarmHomePreview() {
    RetroWarmHomeScreen()
}

@Preview(name = "Retro Warm - Scanner", showBackground = true, widthDp = 400, heightDp = 700)
@Composable
fun RetroWarmScannerPreview() {
    RetroWarmScannerScreen()
}

@Preview(name = "Retro Warm - Flip Phone", showBackground = true, widthDp = 400, heightDp = 700)
@Composable
fun RetroWarmFlipPhonePreview() {
    RetroWarmFlipPhoneScreen()
}

@Preview(name = "Retro Warm - Now Playing", showBackground = true, widthDp = 400, heightDp = 700)
@Composable
fun RetroWarmNowPlayingPreview() {
    RetroWarmNowPlayingScreen()
}
