package com.hitit.app.ui.screens

import androidx.compose.animation.core.Animatable
import androidx.compose.animation.core.tween
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.hitit.app.ui.theme.Accent
import com.hitit.app.ui.theme.Primary
import kotlinx.coroutines.delay

@Composable
fun SplashScreen(
    onSplashComplete: () -> Unit
) {
    val alpha = remember { Animatable(0f) }
    val iconScale = remember { Animatable(0.8f) }

    LaunchedEffect(Unit) {
        // Fade in animation
        alpha.animateTo(1f, animationSpec = tween(500))
        iconScale.animateTo(1f, animationSpec = tween(400))

        // Wait before navigating
        delay(2000)

        onSplashComplete()
    }

    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(Primary),
        contentAlignment = Alignment.Center
    ) {
        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
            modifier = Modifier.alpha(alpha.value)
        ) {
            // Boombox icon
            BoomboxIcon(
                modifier = Modifier.size((180 * iconScale.value).dp)
            )

            Spacer(modifier = Modifier.height(32.dp))

            // App title
            Text(
                text = "BoomBox",
                fontSize = 48.sp,
                fontWeight = FontWeight.Bold,
                color = Color.White
            )

            Spacer(modifier = Modifier.height(8.dp))

            Text(
                text = "Musik-Quiz-Spiel",
                fontSize = 18.sp,
                color = Color.White.copy(alpha = 0.9f)
            )
        }
    }
}

@Composable
private fun BoomboxIcon(modifier: Modifier = Modifier) {
    Canvas(modifier = modifier) {
        val centerX = size.width / 2
        val centerY = size.height / 2
        val scale = size.width / 108f

        // Main body
        val bodyWidth = 56 * scale
        val bodyHeight = 24 * scale
        val bodyLeft = centerX - bodyWidth / 2
        val bodyTop = centerY - bodyHeight / 2 + 4 * scale

        // Body background
        drawRect(
            color = Color.White,
            topLeft = Offset(bodyLeft, bodyTop),
            size = Size(bodyWidth, bodyHeight)
        )

        // Handle
        drawArc(
            color = Color.White.copy(alpha = 0.8f),
            startAngle = 180f,
            sweepAngle = 180f,
            useCenter = false,
            topLeft = Offset(centerX - 12 * scale, bodyTop - 10 * scale),
            size = Size(24 * scale, 14 * scale),
            style = Stroke(width = 3 * scale)
        )

        // Left speaker
        drawCircle(
            color = Color(0xFF333333),
            radius = 9 * scale,
            center = Offset(bodyLeft + 14 * scale, bodyTop + bodyHeight / 2)
        )
        drawCircle(
            color = Color(0xFF555555),
            radius = 6 * scale,
            center = Offset(bodyLeft + 14 * scale, bodyTop + bodyHeight / 2)
        )
        drawCircle(
            color = Color(0xFF222222),
            radius = 2.5f * scale,
            center = Offset(bodyLeft + 14 * scale, bodyTop + bodyHeight / 2)
        )

        // Right speaker
        drawCircle(
            color = Color(0xFF333333),
            radius = 9 * scale,
            center = Offset(bodyLeft + bodyWidth - 14 * scale, bodyTop + bodyHeight / 2)
        )
        drawCircle(
            color = Color(0xFF555555),
            radius = 6 * scale,
            center = Offset(bodyLeft + bodyWidth - 14 * scale, bodyTop + bodyHeight / 2)
        )
        drawCircle(
            color = Color(0xFF222222),
            radius = 2.5f * scale,
            center = Offset(bodyLeft + bodyWidth - 14 * scale, bodyTop + bodyHeight / 2)
        )

        // Cassette deck
        val cassetteWidth = 10 * scale
        val cassetteHeight = 12 * scale
        drawRect(
            color = Color(0xFF444444),
            topLeft = Offset(centerX - cassetteWidth / 2, bodyTop + 2 * scale),
            size = Size(cassetteWidth, cassetteHeight)
        )
        drawRect(
            color = Color(0xFF87CEEB),
            topLeft = Offset(centerX - cassetteWidth / 2 + 1 * scale, bodyTop + 3 * scale),
            size = Size(cassetteWidth - 2 * scale, cassetteHeight - 4 * scale)
        )

        // Musical notes (accent color)
        val noteColor = Accent
        val noteY = bodyTop - 18 * scale

        // Draw three notes
        for (i in 0..2) {
            val noteX = centerX + (i - 1) * 12 * scale

            // Note head
            drawCircle(
                color = noteColor,
                radius = 3.5f * scale,
                center = Offset(noteX, noteY + 8 * scale)
            )

            // Note stem
            drawLine(
                color = noteColor,
                start = Offset(noteX + 3 * scale, noteY + 6 * scale),
                end = Offset(noteX + 3 * scale, noteY - 4 * scale),
                strokeWidth = 2 * scale
            )
        }

        // Connecting beam
        drawLine(
            color = noteColor,
            start = Offset(centerX - 12 * scale + 3 * scale, noteY - 4 * scale),
            end = Offset(centerX + 12 * scale + 3 * scale, noteY - 4 * scale),
            strokeWidth = 2.5f * scale
        )
    }
}
