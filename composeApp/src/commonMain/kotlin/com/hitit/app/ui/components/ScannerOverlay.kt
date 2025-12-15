package com.hitit.app.ui.components

import androidx.compose.foundation.Canvas
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.CornerRadius
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.graphics.BlendMode
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.CompositingStrategy
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.unit.dp

/**
 * Dark overlay with transparent cutout for the scan area
 */
@Composable
fun ScannerOverlay(
    modifier: Modifier = Modifier,
    scanAreaFraction: Float = 0.75f,
    overlayColor: Color = Color.Black.copy(alpha = 0.6f),
    cornerRadius: Float = 16f
) {
    Canvas(
        modifier = modifier.graphicsLayer {
            compositingStrategy = CompositingStrategy.Offscreen
        }
    ) {
        val canvasWidth = size.width
        val canvasHeight = size.height

        // Calculate scan area size (square based on width)
        val scanAreaSize = canvasWidth * scanAreaFraction

        // Center the scan area
        val scanAreaLeft = (canvasWidth - scanAreaSize) / 2
        val scanAreaTop = (canvasHeight - scanAreaSize) / 2

        // Draw full overlay
        drawRect(color = overlayColor)

        // Cut out the scan area (transparent hole)
        drawRoundRect(
            color = Color.Transparent,
            topLeft = Offset(scanAreaLeft, scanAreaTop),
            size = Size(scanAreaSize, scanAreaSize),
            cornerRadius = CornerRadius(cornerRadius.dp.toPx()),
            blendMode = BlendMode.Clear
        )
    }
}
