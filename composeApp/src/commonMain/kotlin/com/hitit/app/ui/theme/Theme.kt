package com.hitit.app.ui.theme

import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color

// Primary brand colors - Vibrant Orange
val Primary = Color(0xFFFF6B35)
val PrimaryDark = Color(0xFFE55A25)
val PrimaryLight = Color(0xFFFF8F5E)
val OnPrimary = Color.White

// Accent - Golden Yellow (music notes)
val Accent = Color(0xFFFFD93D)
val AccentDark = Color(0xFFE5C235)
val OnAccent = Color(0xFF1A1A2E)

// Background colors
val BackgroundDark = Color(0xFF1A1A2E)
val BackgroundLight = Color(0xFFFFF8F0)

// Surface colors
val SurfaceDark = Color(0xFF16213E)
val SurfaceLight = Color(0xFFFFFFFF)
val SurfaceVariantDark = Color(0xFF252545)
val SurfaceVariantLight = Color(0xFFFFEEE0)

// Text colors
val OnBackgroundDark = Color(0xFFF5F5F5)
val OnBackgroundLight = Color(0xFF1A1A2E)
val OnSurfaceDark = Color(0xFFF5F5F5)
val OnSurfaceLight = Color(0xFF1A1A2E)

// Error
val Error = Color(0xFFCF6679)
val OnError = Color.Black

// Success
val Success = Color(0xFF4CAF50)

private val DarkColorScheme = darkColorScheme(
    primary = Primary,
    onPrimary = OnPrimary,
    primaryContainer = PrimaryDark,
    onPrimaryContainer = Color.White,
    secondary = Accent,
    onSecondary = OnAccent,
    secondaryContainer = AccentDark,
    onSecondaryContainer = OnAccent,
    tertiary = PrimaryLight,
    onTertiary = OnPrimary,
    background = BackgroundDark,
    onBackground = OnBackgroundDark,
    surface = SurfaceDark,
    onSurface = OnSurfaceDark,
    surfaceVariant = SurfaceVariantDark,
    onSurfaceVariant = Color(0xFFB0B0B0),
    error = Error,
    onError = OnError,
)

private val LightColorScheme = lightColorScheme(
    primary = Primary,
    onPrimary = OnPrimary,
    primaryContainer = PrimaryLight,
    onPrimaryContainer = Color(0xFF1A1A2E),
    secondary = Accent,
    onSecondary = OnAccent,
    secondaryContainer = Color(0xFFFFECB3),
    onSecondaryContainer = OnAccent,
    tertiary = PrimaryDark,
    onTertiary = OnPrimary,
    background = BackgroundLight,
    onBackground = OnBackgroundLight,
    surface = SurfaceLight,
    onSurface = OnSurfaceLight,
    surfaceVariant = SurfaceVariantLight,
    onSurfaceVariant = Color(0xFF5A5A5A),
    error = Error,
    onError = OnError,
)

@Composable
fun HitItTheme(
    darkTheme: Boolean = isSystemInDarkTheme(),
    content: @Composable () -> Unit
) {
    val colorScheme = if (darkTheme) DarkColorScheme else LightColorScheme

    MaterialTheme(
        colorScheme = colorScheme,
        typography = Typography(),
        content = content
    )
}
