package com.hitit.app.ui.components

import androidx.compose.runtime.Composable

@Composable
expect fun PlatformBackHandler(enabled: Boolean = true, onBack: () -> Unit)
