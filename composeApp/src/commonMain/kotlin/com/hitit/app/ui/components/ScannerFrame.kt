package com.hitit.app.ui.components

import androidx.compose.foundation.Canvas
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.StrokeCap
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp

/**
 * Cyan scanning frame overlay for QR code detection area
 */
@Composable
fun ScannerFrame(
    modifier: Modifier = Modifier,
    frameColor: Color = Color(0xFF00D4FF),
    cornerLength: Dp = 40.dp,
    cornerRadius: Dp = 16.dp,
    strokeWidth: Dp = 4.dp
) {
    Canvas(modifier = modifier) {
        val stroke = strokeWidth.toPx()
        val corner = cornerLength.toPx()
        val radius = cornerRadius.toPx()
        val frameWidth = size.width
        val frameHeight = size.height

        // Draw corner brackets
        val cornerStyle = Stroke(width = stroke, cap = StrokeCap.Round)

        // Top-left corner
        drawLine(
            color = frameColor,
            start = Offset(0f, radius + corner),
            end = Offset(0f, radius),
            strokeWidth = stroke,
            cap = StrokeCap.Round
        )
        drawArc(
            color = frameColor,
            startAngle = 180f,
            sweepAngle = 90f,
            useCenter = false,
            topLeft = Offset(0f, 0f),
            size = Size(radius * 2, radius * 2),
            style = cornerStyle
        )
        drawLine(
            color = frameColor,
            start = Offset(radius, 0f),
            end = Offset(radius + corner, 0f),
            strokeWidth = stroke,
            cap = StrokeCap.Round
        )

        // Top-right corner
        drawLine(
            color = frameColor,
            start = Offset(frameWidth - radius - corner, 0f),
            end = Offset(frameWidth - radius, 0f),
            strokeWidth = stroke,
            cap = StrokeCap.Round
        )
        drawArc(
            color = frameColor,
            startAngle = 270f,
            sweepAngle = 90f,
            useCenter = false,
            topLeft = Offset(frameWidth - radius * 2, 0f),
            size = Size(radius * 2, radius * 2),
            style = cornerStyle
        )
        drawLine(
            color = frameColor,
            start = Offset(frameWidth, radius),
            end = Offset(frameWidth, radius + corner),
            strokeWidth = stroke,
            cap = StrokeCap.Round
        )

        // Bottom-right corner
        drawLine(
            color = frameColor,
            start = Offset(frameWidth, frameHeight - radius - corner),
            end = Offset(frameWidth, frameHeight - radius),
            strokeWidth = stroke,
            cap = StrokeCap.Round
        )
        drawArc(
            color = frameColor,
            startAngle = 0f,
            sweepAngle = 90f,
            useCenter = false,
            topLeft = Offset(frameWidth - radius * 2, frameHeight - radius * 2),
            size = Size(radius * 2, radius * 2),
            style = cornerStyle
        )
        drawLine(
            color = frameColor,
            start = Offset(frameWidth - radius, frameHeight),
            end = Offset(frameWidth - radius - corner, frameHeight),
            strokeWidth = stroke,
            cap = StrokeCap.Round
        )

        // Bottom-left corner
        drawLine(
            color = frameColor,
            start = Offset(radius + corner, frameHeight),
            end = Offset(radius, frameHeight),
            strokeWidth = stroke,
            cap = StrokeCap.Round
        )
        drawArc(
            color = frameColor,
            startAngle = 90f,
            sweepAngle = 90f,
            useCenter = false,
            topLeft = Offset(0f, frameHeight - radius * 2),
            size = Size(radius * 2, radius * 2),
            style = cornerStyle
        )
        drawLine(
            color = frameColor,
            start = Offset(0f, frameHeight - radius),
            end = Offset(0f, frameHeight - radius - corner),
            strokeWidth = stroke,
            cap = StrokeCap.Round
        )
    }
}
