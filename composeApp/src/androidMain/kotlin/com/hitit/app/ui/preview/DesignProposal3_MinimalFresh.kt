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
import androidx.compose.ui.geometry.CornerRadius
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Path
import androidx.compose.ui.graphics.StrokeCap
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

/**
 * Design Proposal 3: Neon Flat
 *
 * Visual Style: Flat design with bold typography
 * - Clean, minimal UI with lots of whitespace
 * - Bold geometric shapes
 * - Neon Cyber color theme from Proposal 1
 */
object MinimalFreshColors {
    val Primary = Color(0xFFFF00FF)        // Magenta (from Neon Cyber)
    val PrimaryLight = Color(0xFFFF66FF)
    val Secondary = Color(0xFF00FFFF)      // Cyan (from Neon Cyber)
    val Accent = Color(0xFFFF6B35)         // Orange (from Neon Cyber)

    val BackgroundLight = Color(0xFF1A0A2E)  // Dark purple background
    val BackgroundDark = Color(0xFF0D0221)

    val Surface = Color(0x22FFFFFF)        // Frosted glass
    val SurfaceDark = Color(0xFF150520)
    val SurfaceBorder = Color(0x44FFFFFF)
    val SurfaceBorderDark = Color(0x33FFFFFF)

    val TextPrimary = Color.White
    val TextPrimaryDark = Color.White
    val TextSecondary = Color(0xAAFFFFFF)
    val TextSecondaryDark = Color(0x88FFFFFF)

}

// ==================== FLAT DESIGN COMPONENTS ====================

@Composable
private fun FlatCard(
    modifier: Modifier = Modifier,
    content: @Composable () -> Unit
) {
    Box(
        modifier = modifier
            .clip(RoundedCornerShape(12.dp))
            .background(MinimalFreshColors.Surface)
            .border(
                width = 1.dp,
                color = MinimalFreshColors.SurfaceBorder,
                shape = RoundedCornerShape(12.dp)
            )
    ) {
        content()
    }
}

@Composable
private fun FlatButton(
    text: String,
    onClick: () -> Unit,
    modifier: Modifier = Modifier,
    color: Color = MinimalFreshColors.Primary
) {
    Box(
        modifier = modifier
            .height(56.dp)
            .clip(RoundedCornerShape(12.dp))
            .background(color),
        contentAlignment = Alignment.Center
    ) {
        Text(
            text = text,
            fontSize = 16.sp,
            fontWeight = FontWeight.SemiBold,
            color = Color.White
        )
    }
}

// ==================== SPLASH SCREEN ====================

@Composable
fun MinimalFreshSplashScreen(modifier: Modifier = Modifier) {
    Box(
        modifier = modifier
            .fillMaxWidth()
            .height(700.dp)
            .background(Brush.verticalGradient(listOf(MinimalFreshColors.BackgroundLight, MinimalFreshColors.BackgroundDark))),
        contentAlignment = Alignment.Center
    ) {
        Column(
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            // Geometric boombox icon in rounded gradient frame
            Box(
                modifier = Modifier
                    .size(200.dp)
                    .clip(RoundedCornerShape(32.dp))
                    .background(MinimalFreshColors.Surface)
                    .border(
                        width = 3.dp,
                        brush = Brush.linearGradient(
                            listOf(MinimalFreshColors.Primary, MinimalFreshColors.Secondary)
                        ),
                        shape = RoundedCornerShape(32.dp)
                    ),
                contentAlignment = Alignment.Center
            ) {
                MinimalSpeakerIcon(
                    modifier = Modifier.size(160.dp, 100.dp)
                )
            }

            Spacer(modifier = Modifier.height(48.dp))

            // Clean bold typography - centered
            Text(
                text = "DUKESTAR",
                fontSize = 42.sp,
                fontWeight = FontWeight.Black,
                color = Color.White,
                letterSpacing = 4.sp
            )

            Spacer(modifier = Modifier.height(12.dp))

            // Subtitle with accent line
            Row(
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.spacedBy(12.dp)
            ) {
                Box(
                    Modifier
                        .width(24.dp)
                        .height(2.dp)
                        .background(MinimalFreshColors.Primary)
                )
                Text(
                    text = "MUSIC QUIZ",
                    fontSize = 14.sp,
                    fontWeight = FontWeight.Medium,
                    color = MinimalFreshColors.TextSecondary,
                    letterSpacing = 3.sp
                )
                Box(
                    Modifier
                        .width(24.dp)
                        .height(2.dp)
                        .background(MinimalFreshColors.Secondary)
                )
            }
        }
    }
}

@Composable
private fun MinimalSpeakerIcon(modifier: Modifier = Modifier) {
    Canvas(modifier = modifier) {
        val centerX = size.width / 2
        val centerY = size.height / 2

        // Simple geometric boombox
        val boxWidth = size.width * 0.9f
        val boxHeight = size.height * 0.5f
        val boxLeft = (size.width - boxWidth) / 2
        val boxTop = centerY - boxHeight / 2

        // Main body - rounded rectangle
        drawRoundRect(
            color = Color.White,
            topLeft = Offset(boxLeft, boxTop),
            size = Size(boxWidth, boxHeight),
            cornerRadius = CornerRadius(12.dp.toPx())
        )

        // Handle
        drawRoundRect(
            color = Color.White,
            topLeft = Offset(centerX - 30.dp.toPx(), boxTop - 16.dp.toPx()),
            size = Size(60.dp.toPx(), 20.dp.toPx()),
            cornerRadius = CornerRadius(10.dp.toPx())
        )

        // Left speaker - simple circle
        val speakerRadius = boxHeight * 0.35f
        drawCircle(
            color = MinimalFreshColors.Primary,
            radius = speakerRadius,
            center = Offset(boxLeft + boxWidth * 0.25f, centerY)
        )
        drawCircle(
            color = Color.White,
            radius = speakerRadius * 0.4f,
            center = Offset(boxLeft + boxWidth * 0.25f, centerY)
        )

        // Right speaker
        drawCircle(
            color = MinimalFreshColors.Secondary,
            radius = speakerRadius,
            center = Offset(boxLeft + boxWidth * 0.75f, centerY)
        )
        drawCircle(
            color = Color.White,
            radius = speakerRadius * 0.4f,
            center = Offset(boxLeft + boxWidth * 0.75f, centerY)
        )

        // Center display
        drawRoundRect(
            color = MinimalFreshColors.Accent,
            topLeft = Offset(centerX - 15.dp.toPx(), centerY - 12.dp.toPx()),
            size = Size(30.dp.toPx(), 24.dp.toPx()),
            cornerRadius = CornerRadius(4.dp.toPx())
        )
    }
}

// ==================== HOME SCREEN ====================

@Composable
fun MinimalFreshHomeScreen(modifier: Modifier = Modifier) {
    Box(
        modifier = modifier
            .fillMaxWidth()
            .height(700.dp)
            .background(Brush.verticalGradient(listOf(MinimalFreshColors.BackgroundLight, MinimalFreshColors.BackgroundDark)))
    ) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(24.dp),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center
        ) {
            Text(
                text = "BoomBox",
                fontSize = 32.sp,
                fontWeight = FontWeight.Black,
                color = MinimalFreshColors.TextPrimary,
                letterSpacing = (-0.5).sp
            )

            Spacer(modifier = Modifier.height(4.dp))

            Text(
                text = "MUSIC QUIZ GAME",
                fontSize = 12.sp,
                fontWeight = FontWeight.Medium,
                color = MinimalFreshColors.TextSecondary,
                letterSpacing = 3.sp
            )

            Spacer(modifier = Modifier.height(56.dp))

            // Glass card
            FlatCard(
                modifier = Modifier.fillMaxWidth()
            ) {
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(20.dp),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Text(
                        text = "How to Play",
                        fontSize = 16.sp,
                        fontWeight = FontWeight.SemiBold,
                        color = MinimalFreshColors.TextPrimary
                    )
                    Icon(
                        imageVector = Icons.Default.KeyboardArrowDown,
                        contentDescription = null,
                        tint = MinimalFreshColors.Secondary
                    )
                }
            }

            Spacer(modifier = Modifier.height(56.dp))

            // Gradient button
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(56.dp)
                    .clip(RoundedCornerShape(12.dp))
                    .background(Brush.horizontalGradient(listOf(MinimalFreshColors.Primary, MinimalFreshColors.Secondary))),
                contentAlignment = Alignment.Center
            ) {
                Text(
                    text = "Start Game",
                    fontSize = 16.sp,
                    fontWeight = FontWeight.Bold,
                    color = Color.White
                )
            }
        }
    }
}

@Composable
private fun MinimalSpeakerIconSmall(modifier: Modifier = Modifier) {
    Canvas(modifier = modifier) {
        val centerX = size.width / 2
        val centerY = size.height / 2

        // Two circles for speakers
        drawCircle(
            color = Color.White,
            radius = size.width * 0.25f,
            center = Offset(centerX - size.width * 0.2f, centerY)
        )
        drawCircle(
            color = Color.White,
            radius = size.width * 0.25f,
            center = Offset(centerX + size.width * 0.2f, centerY)
        )
    }
}

// ==================== SCANNER SCREEN ====================

@Composable
fun MinimalFreshScannerScreen(modifier: Modifier = Modifier) {
    Column(
        modifier = modifier
            .fillMaxWidth()
            .height(700.dp)
            .background(MinimalFreshColors.BackgroundDark)
    ) {
        // Gradient top bar
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .background(Brush.horizontalGradient(listOf(MinimalFreshColors.Primary, MinimalFreshColors.Secondary)))
                .padding(horizontal = 8.dp, vertical = 12.dp)
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
                .fillMaxWidth()
                .height(450.dp)
                .background(Color(0xFF0A0A1A)),
            contentAlignment = Alignment.Center
        ) {
            // Gradient border frame
            Box(
                modifier = Modifier
                    .size(250.dp)
                    .border(3.dp, Brush.linearGradient(listOf(MinimalFreshColors.Primary, MinimalFreshColors.Secondary)), RoundedCornerShape(16.dp))
            )
        }

        // Bottom controls
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .background(MinimalFreshColors.Surface)
                .padding(24.dp)
        ) {
            Column(
                modifier = Modifier.fillMaxWidth(),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Text(
                    text = "Point camera at QR code",
                    fontSize = 16.sp,
                    fontWeight = FontWeight.Medium,
                    color = MinimalFreshColors.TextPrimary
                )

                Spacer(modifier = Modifier.height(20.dp))

                OutlinedButton(
                    onClick = {},
                    colors = ButtonDefaults.outlinedButtonColors(
                        contentColor = MinimalFreshColors.Secondary
                    ),
                    shape = RoundedCornerShape(8.dp)
                ) {
                    Text(
                        text = "Flash On",
                        fontWeight = FontWeight.SemiBold
                    )
                }
            }
        }
    }
}

@Composable
private fun MinimalScannerFrame(modifier: Modifier = Modifier) {
    Canvas(modifier = modifier) {
        val strokeWidth = 4.dp.toPx()
        val cornerLength = 30.dp.toPx()

        // Simple L-shaped corners - clean and minimal
        val purple = MinimalFreshColors.Primary
        val teal = MinimalFreshColors.Secondary

        // Top-left (purple)
        drawLine(
            color = purple,
            start = Offset(0f, 0f),
            end = Offset(0f, cornerLength),
            strokeWidth = strokeWidth,
            cap = StrokeCap.Square
        )
        drawLine(
            color = purple,
            start = Offset(0f, 0f),
            end = Offset(cornerLength, 0f),
            strokeWidth = strokeWidth,
            cap = StrokeCap.Square
        )

        // Top-right (teal)
        drawLine(
            color = teal,
            start = Offset(size.width, 0f),
            end = Offset(size.width, cornerLength),
            strokeWidth = strokeWidth,
            cap = StrokeCap.Square
        )
        drawLine(
            color = teal,
            start = Offset(size.width, 0f),
            end = Offset(size.width - cornerLength, 0f),
            strokeWidth = strokeWidth,
            cap = StrokeCap.Square
        )

        // Bottom-left (teal)
        drawLine(
            color = teal,
            start = Offset(0f, size.height),
            end = Offset(0f, size.height - cornerLength),
            strokeWidth = strokeWidth,
            cap = StrokeCap.Square
        )
        drawLine(
            color = teal,
            start = Offset(0f, size.height),
            end = Offset(cornerLength, size.height),
            strokeWidth = strokeWidth,
            cap = StrokeCap.Square
        )

        // Bottom-right (purple)
        drawLine(
            color = purple,
            start = Offset(size.width, size.height),
            end = Offset(size.width, size.height - cornerLength),
            strokeWidth = strokeWidth,
            cap = StrokeCap.Square
        )
        drawLine(
            color = purple,
            start = Offset(size.width, size.height),
            end = Offset(size.width - cornerLength, size.height),
            strokeWidth = strokeWidth,
            cap = StrokeCap.Square
        )
    }
}

// ==================== FLIP PHONE SCREEN ====================

@Composable
fun MinimalFreshFlipPhoneScreen(modifier: Modifier = Modifier) {
    Box(
        modifier = modifier
            .fillMaxWidth()
            .height(700.dp)
            .background(Brush.verticalGradient(listOf(MinimalFreshColors.BackgroundLight, MinimalFreshColors.BackgroundDark)))
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
            // Phone shape with gradient border
            Box(
                Modifier
                    .width(120.dp)
                    .height(200.dp)
                    .clip(RoundedCornerShape(24.dp))
                    .border(3.dp, Brush.linearGradient(listOf(MinimalFreshColors.Primary, MinimalFreshColors.Secondary)), RoundedCornerShape(24.dp))
            )

            Spacer(modifier = Modifier.height(48.dp))

            Text(
                text = "Flip Phone\nFace Down",
                fontSize = 32.sp,
                fontWeight = FontWeight.Black,
                color = MinimalFreshColors.TextPrimary,
                textAlign = TextAlign.Center,
                lineHeight = 36.sp
            )

            Spacer(modifier = Modifier.height(12.dp))

            Text(
                text = "Music starts automatically",
                fontSize = 16.sp,
                color = MinimalFreshColors.TextSecondary,
                textAlign = TextAlign.Center
            )

            Spacer(modifier = Modifier.height(40.dp))

            // Mode selection chips
            Row(
                horizontalArrangement = Arrangement.spacedBy(12.dp)
            ) {
                MinimalChip(text = "Preview", selected = true)
                MinimalChip(text = "Deezer", selected = false)
            }
        }
    }
}

@Composable
private fun MinimalPhoneIllustration(modifier: Modifier = Modifier) {
    Canvas(modifier = modifier) {
        val strokeWidth = 3.dp.toPx()
        val cornerRadius = 28.dp.toPx()
        val phoneWidth = size.width * 0.75f
        val phoneHeight = size.height * 0.9f
        val phoneLeft = (size.width - phoneWidth) / 2
        val phoneTop = (size.height - phoneHeight) / 2

        // Phone outline - clean stroke
        drawRoundRect(
            color = MinimalFreshColors.Primary,
            topLeft = Offset(phoneLeft, phoneTop),
            size = Size(phoneWidth, phoneHeight),
            cornerRadius = CornerRadius(cornerRadius),
            style = Stroke(width = strokeWidth)
        )

        // Notch - simple line
        val notchWidth = phoneWidth * 0.25f
        val notchY = phoneTop + 20.dp.toPx()
        drawLine(
            color = MinimalFreshColors.Primary,
            start = Offset((size.width - notchWidth) / 2, notchY),
            end = Offset((size.width + notchWidth) / 2, notchY),
            strokeWidth = strokeWidth,
            cap = StrokeCap.Round
        )

        // Arrow down indicator
        val arrowY = size.height * 0.6f
        val arrowSize = 20.dp.toPx()
        drawLine(
            color = MinimalFreshColors.Secondary,
            start = Offset(size.width / 2, arrowY - arrowSize),
            end = Offset(size.width / 2, arrowY + arrowSize),
            strokeWidth = 3.dp.toPx(),
            cap = StrokeCap.Round
        )
        // Arrow head
        drawLine(
            color = MinimalFreshColors.Secondary,
            start = Offset(size.width / 2, arrowY + arrowSize),
            end = Offset(size.width / 2 - 10.dp.toPx(), arrowY + arrowSize - 10.dp.toPx()),
            strokeWidth = 3.dp.toPx(),
            cap = StrokeCap.Round
        )
        drawLine(
            color = MinimalFreshColors.Secondary,
            start = Offset(size.width / 2, arrowY + arrowSize),
            end = Offset(size.width / 2 + 10.dp.toPx(), arrowY + arrowSize - 10.dp.toPx()),
            strokeWidth = 3.dp.toPx(),
            cap = StrokeCap.Round
        )
    }
}

@Composable
private fun MinimalChip(
    text: String,
    selected: Boolean
) {
    val backgroundColor = if (selected) MinimalFreshColors.Primary else MinimalFreshColors.Surface
    val textColor = if (selected) Color.White else MinimalFreshColors.TextSecondary
    val borderColor = if (selected) MinimalFreshColors.Primary else MinimalFreshColors.SurfaceBorder

    Box(
        modifier = Modifier
            .clip(RoundedCornerShape(8.dp))
            .background(backgroundColor)
            .border(
                width = if (selected) 0.dp else 1.dp,
                color = borderColor,
                shape = RoundedCornerShape(8.dp)
            )
            .padding(horizontal = 20.dp, vertical = 12.dp)
    ) {
        Text(
            text = text,
            fontSize = 14.sp,
            fontWeight = FontWeight.SemiBold,
            color = textColor
        )
    }
}

// ==================== NOW PLAYING SCREEN ====================

@Composable
fun MinimalFreshNowPlayingScreen(modifier: Modifier = Modifier) {
    Box(
        modifier = modifier
            .fillMaxWidth()
            .height(700.dp)
            .background(Brush.verticalGradient(listOf(MinimalFreshColors.BackgroundLight, MinimalFreshColors.BackgroundDark)))
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
            // Album art with gradient border
            Box(
                modifier = Modifier
                    .size(180.dp)
                    .clip(RoundedCornerShape(16.dp))
                    .border(3.dp, Brush.linearGradient(listOf(MinimalFreshColors.Primary, MinimalFreshColors.Secondary)), RoundedCornerShape(16.dp))
                    .background(MinimalFreshColors.Primary.copy(alpha = 0.2f)),
                contentAlignment = Alignment.Center
            ) {
                Text("♪", fontSize = 64.sp, color = Color.White)
            }

            Spacer(modifier = Modifier.height(32.dp))

            // Track title
            Text(
                text = "Billie Jean",
                fontSize = 26.sp,
                fontWeight = FontWeight.Bold,
                color = MinimalFreshColors.TextPrimary,
                textAlign = TextAlign.Center
            )

            Spacer(modifier = Modifier.height(8.dp))

            // Artist
            Text(
                text = "Michael Jackson",
                fontSize = 18.sp,
                color = MinimalFreshColors.TextSecondary,
                textAlign = TextAlign.Center
            )

            Spacer(modifier = Modifier.height(16.dp))

            // Year badge
            Box(
                modifier = Modifier
                    .clip(RoundedCornerShape(20.dp))
                    .background(MinimalFreshColors.Secondary.copy(alpha = 0.2f))
                    .border(1.dp, MinimalFreshColors.Secondary, RoundedCornerShape(20.dp))
                    .padding(horizontal = 24.dp, vertical = 8.dp)
            ) {
                Text(
                    text = "1982",
                    fontSize = 22.sp,
                    fontWeight = FontWeight.Bold,
                    color = MinimalFreshColors.Secondary
                )
            }

            Spacer(modifier = Modifier.height(32.dp))

            // Play button with gradient
            Box(
                modifier = Modifier
                    .size(64.dp)
                    .clip(CircleShape)
                    .background(Brush.linearGradient(listOf(MinimalFreshColors.Primary, MinimalFreshColors.Secondary))),
                contentAlignment = Alignment.Center
            ) {
                Icon(
                    imageVector = Icons.Default.PlayArrow,
                    contentDescription = "Playing",
                    modifier = Modifier.size(32.dp),
                    tint = Color.White
                )
            }

            Spacer(modifier = Modifier.height(48.dp))

            // Next card button with gradient
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(56.dp)
                    .clip(RoundedCornerShape(12.dp))
                    .background(Brush.horizontalGradient(listOf(MinimalFreshColors.Primary, MinimalFreshColors.Secondary))),
                contentAlignment = Alignment.Center
            ) {
                Text(
                    text = "Next Card",
                    fontWeight = FontWeight.Bold,
                    color = Color.White
                )
            }
        }
    }
}

// ==================== PREVIEWS ====================

//@Preview(
//    name = "Minimal Fresh - All Screens",
//    showBackground = true,
//    widthDp = 400,
//    heightDp = 3600
//)
//@Composable
//fun MinimalFreshFullPreview() {
//    Column {
//        Text(
//            text = "PROPOSAL 3: MINIMAL FRESH",
//            modifier = Modifier
//                .fillMaxWidth()
//                .background(MinimalFreshColors.Primary)
//                .padding(16.dp),
//            fontSize = 20.sp,
//            fontWeight = FontWeight.Bold,
//            color = Color.White,
//            textAlign = TextAlign.Center
//        )
//        MinimalFreshSplashScreen()
//        Spacer(modifier = Modifier.height(16.dp).background(MinimalFreshColors.SurfaceBorder))
//        MinimalFreshHomeScreen()
//        Spacer(modifier = Modifier.height(16.dp).background(MinimalFreshColors.SurfaceBorder))
//        MinimalFreshScannerScreen()
//        Spacer(modifier = Modifier.height(16.dp).background(MinimalFreshColors.SurfaceBorder))
//        MinimalFreshFlipPhoneScreen()
//        Spacer(modifier = Modifier.height(16.dp).background(MinimalFreshColors.SurfaceBorder))
//        MinimalFreshNowPlayingScreen()
//    }
//}
//
@Preview(name = "Minimal Fresh - Splash", showBackground = true, widthDp = 400, heightDp = 700)
@Composable
fun MinimalFreshSplashPreview() {
    MinimalFreshSplashScreen()
}

@Preview(name = "Minimal Fresh - Home", showBackground = true, widthDp = 400, heightDp = 700)
@Composable
fun MinimalFreshHomePreview() {
    MinimalFreshHomeScreen()
}

@Preview(name = "Minimal Fresh - Scanner", showBackground = true, widthDp = 400, heightDp = 700)
@Composable
fun MinimalFreshScannerPreview() {
    MinimalFreshScannerScreen()
}

@Preview(name = "Minimal Fresh - Flip Phone", showBackground = true, widthDp = 400, heightDp = 700)
@Composable
fun MinimalFreshFlipPhonePreview() {
    MinimalFreshFlipPhoneScreen()
}

@Preview(name = "Minimal Fresh - Now Playing", showBackground = true, widthDp = 400, heightDp = 700)
@Composable
fun MinimalFreshNowPlayingPreview() {
    MinimalFreshNowPlayingScreen()
}
