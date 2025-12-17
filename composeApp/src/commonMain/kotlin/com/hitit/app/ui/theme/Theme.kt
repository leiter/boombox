package com.hitit.app.ui.theme

import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color

// Primary brand colors - Neon Cyber (Magenta)
val Primary = Color(0xFFFF00FF)
val PrimaryDark = Color(0xFFCC00CC)
val PrimaryLight = Color(0xFFFF66FF)
val OnPrimary = Color.White

// Secondary - Cyan
val Secondary = Color(0xFF00FFFF)
val SecondaryDark = Color(0xFF00CCCC)
val OnSecondary = Color(0xFF0D0221)

// Accent - Orange
val Accent = Color(0xFFFF6B35)
val AccentDark = Color(0xFFE55A25)
val OnAccent = Color.White

// Background colors - Dark Purple
val BackgroundDark = Color(0xFF0D0221)
val BackgroundLight = Color(0xFF1A0A2E)

// Surface colors - Frosted glass
val SurfaceDark = Color(0xFF150520)
val SurfaceLight = Color(0x22FFFFFF)
val SurfaceVariantDark = Color(0xFF1A0A2E)
val SurfaceVariantLight = Color(0x33FFFFFF)
val SurfaceBorder = Color(0x44FFFFFF)

// Text colors
val OnBackgroundDark = Color.White
val OnBackgroundLight = Color.White
val OnSurfaceDark = Color.White
val OnSurfaceLight = Color.White
val TextSecondary = Color(0xAAFFFFFF)

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
    secondary = Secondary,
    onSecondary = OnSecondary,
    secondaryContainer = SecondaryDark,
    onSecondaryContainer = OnSecondary,
    tertiary = Accent,
    onTertiary = OnAccent,
    background = BackgroundDark,
    onBackground = OnBackgroundDark,
    surface = SurfaceDark,
    onSurface = OnSurfaceDark,
    surfaceVariant = SurfaceVariantDark,
    onSurfaceVariant = TextSecondary,
    error = Error,
    onError = OnError,
)

// Use same dark theme for both - neon cyber is a dark theme
private val LightColorScheme = darkColorScheme(
    primary = Primary,
    onPrimary = OnPrimary,
    primaryContainer = PrimaryDark,
    onPrimaryContainer = Color.White,
    secondary = Secondary,
    onSecondary = OnSecondary,
    secondaryContainer = SecondaryDark,
    onSecondaryContainer = OnSecondary,
    tertiary = Accent,
    onTertiary = OnAccent,
    background = BackgroundLight,
    onBackground = OnBackgroundLight,
    surface = SurfaceVariantDark,
    onSurface = OnSurfaceLight,
    surfaceVariant = SurfaceVariantLight,
    onSurfaceVariant = TextSecondary,
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
