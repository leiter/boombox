package com.hitit.app.ui.screens

import androidx.compose.animation.core.Animatable
import androidx.compose.animation.core.tween
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.draw.clip
import androidx.compose.ui.geometry.CornerRadius
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.hitit.app.ui.theme.Accent
import com.hitit.app.ui.theme.BackgroundDark
import com.hitit.app.ui.theme.BackgroundLight
import com.hitit.app.ui.theme.Primary
import com.hitit.app.ui.theme.Secondary
import com.hitit.app.ui.theme.SurfaceLight
import com.hitit.app.ui.theme.TextSecondary
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
            .background(Brush.verticalGradient(listOf(BackgroundLight, BackgroundDark))),
        contentAlignment = Alignment.Center
    ) {
        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
            modifier = Modifier.alpha(alpha.value)
        ) {
            // Boombox icon in gradient frame
            Box(
                modifier = Modifier
                    .size((200 * iconScale.value).dp)
                    .clip(RoundedCornerShape(32.dp))
                    .background(SurfaceLight)
                    .border(
                        width = 3.dp,
                        brush = Brush.linearGradient(listOf(Primary, Secondary)),
                        shape = RoundedCornerShape(32.dp)
                    ),
                contentAlignment = Alignment.Center
            ) {
                BoomboxIcon(
                    modifier = Modifier.size(160.dp, 100.dp)
                )
            }

            Spacer(modifier = Modifier.height(48.dp))

            // App title
            Text(
                text = "DUKESTAR",
                fontSize = 42.sp,
                fontWeight = FontWeight.Black,
                color = Color.White,
                letterSpacing = 4.sp
            )

            Spacer(modifier = Modifier.height(12.dp))

            // Subtitle with accent lines
            Row(
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.spacedBy(12.dp)
            ) {
                Box(
                    Modifier
                        .width(24.dp)
                        .height(2.dp)
                        .background(Primary)
                )
                Text(
                    text = "MUSIC QUIZ",
                    fontSize = 14.sp,
                    fontWeight = FontWeight.Medium,
                    color = TextSecondary,
                    letterSpacing = 3.sp
                )
                Box(
                    Modifier
                        .width(24.dp)
                        .height(2.dp)
                        .background(Secondary)
                )
            }
        }
    }
}

@Composable
private fun BoomboxIcon(modifier: Modifier = Modifier) {
    Canvas(modifier = modifier) {
        val centerX = size.width / 2
        val centerY = size.height / 2

        // Neon Cyber colors
        val magenta = Color(0xFFFF00FF)
        val cyan = Color(0xFF00FFFF)
        val orange = Color(0xFFFF6B35)

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

        // Left speaker - magenta
        val speakerRadius = boxHeight * 0.35f
        drawCircle(
            color = magenta,
            radius = speakerRadius,
            center = Offset(boxLeft + boxWidth * 0.25f, centerY)
        )
        drawCircle(
            color = Color.White,
            radius = speakerRadius * 0.4f,
            center = Offset(boxLeft + boxWidth * 0.25f, centerY)
        )

        // Right speaker - cyan
        drawCircle(
            color = cyan,
            radius = speakerRadius,
            center = Offset(boxLeft + boxWidth * 0.75f, centerY)
        )
        drawCircle(
            color = Color.White,
            radius = speakerRadius * 0.4f,
            center = Offset(boxLeft + boxWidth * 0.75f, centerY)
        )

        // Center display - orange
        drawRoundRect(
            color = orange,
            topLeft = Offset(centerX - 15.dp.toPx(), centerY - 12.dp.toPx()),
            size = Size(30.dp.toPx(), 24.dp.toPx()),
            cornerRadius = CornerRadius(4.dp.toPx())
        )
    }
}
