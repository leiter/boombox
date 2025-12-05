package com.hitit.app.ui.screens

import androidx.compose.foundation.Canvas
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.systemBars
import androidx.compose.foundation.layout.windowInsetsPadding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Close
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.CornerRadius
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.StrokeCap
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import hitit.composeapp.generated.resources.*
import org.jetbrains.compose.resources.stringResource

/**
 * Screen shown while waiting for user to flip phone face-down to start music
 */
@Composable
fun FlipPhoneScreen(
    title: String? = null,
    artist: String? = null,
    onClose: () -> Unit
) {
    val cyanColor = Color(0xFF00D4FF)

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
            // Phone illustration
            PhoneIllustration(
                modifier = Modifier.size(200.dp, 320.dp),
                color = cyanColor
            )

            Spacer(modifier = Modifier.height(48.dp))

            // Main text
            Text(
                text = stringResource(Res.string.flip_phone_title),
                style = MaterialTheme.typography.headlineMedium,
                fontWeight = FontWeight.Bold,
                color = Color.White,
                textAlign = TextAlign.Center
            )

            Spacer(modifier = Modifier.height(12.dp))

            // Subtitle
            Text(
                text = stringResource(Res.string.flip_phone_subtitle),
                style = MaterialTheme.typography.bodyLarge,
                color = Color.White.copy(alpha = 0.7f),
                textAlign = TextAlign.Center
            )

            // Show track info if available
            if (title != null && artist != null) {
                Spacer(modifier = Modifier.height(32.dp))
                Text(
                    text = "$artist - $title",
                    style = MaterialTheme.typography.bodyMedium,
                    color = cyanColor,
                    textAlign = TextAlign.Center
                )
            }
        }
    }
}

/**
 * Draws a simple phone outline illustration
 */
@Composable
private fun PhoneIllustration(
    modifier: Modifier = Modifier,
    color: Color = Color(0xFF00D4FF)
) {
    Canvas(modifier = modifier) {
        val strokeWidth = 4.dp.toPx()
        val cornerRadius = 32.dp.toPx()
        val phoneWidth = size.width * 0.7f
        val phoneHeight = size.height * 0.85f
        val phoneLeft = (size.width - phoneWidth) / 2
        val phoneTop = (size.height - phoneHeight) / 2

        // Phone body outline
        drawRoundRect(
            color = color,
            topLeft = Offset(phoneLeft, phoneTop),
            size = Size(phoneWidth, phoneHeight),
            cornerRadius = CornerRadius(cornerRadius, cornerRadius),
            style = Stroke(width = strokeWidth, cap = StrokeCap.Round)
        )

        // Notch/speaker at top
        val notchWidth = phoneWidth * 0.3f
        val notchHeight = 8.dp.toPx()
        val notchLeft = (size.width - notchWidth) / 2
        val notchTop = phoneTop + 20.dp.toPx()

        drawRoundRect(
            color = color,
            topLeft = Offset(notchLeft, notchTop),
            size = Size(notchWidth, notchHeight),
            cornerRadius = CornerRadius(notchHeight / 2, notchHeight / 2),
            style = Stroke(width = strokeWidth / 2)
        )

        // Screen area (inner rectangle)
        val screenPadding = 16.dp.toPx()
        val screenCornerRadius = 20.dp.toPx()
        drawRoundRect(
            color = color.copy(alpha = 0.3f),
            topLeft = Offset(phoneLeft + screenPadding, phoneTop + 40.dp.toPx()),
            size = Size(phoneWidth - screenPadding * 2, phoneHeight - 60.dp.toPx()),
            cornerRadius = CornerRadius(screenCornerRadius, screenCornerRadius),
            style = Stroke(width = strokeWidth / 2)
        )
    }
}
