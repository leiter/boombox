package com.hitit.app.ui.components

import androidx.compose.runtime.Composable

@Composable
actual fun PlatformBackHandler(enabled: Boolean, onBack: () -> Unit) {
    // iOS doesn't have a system back button like Android
    // Navigation is handled differently on iOS (swipe gestures, navigation bar buttons)
    // This is a no-op for iOS
}
