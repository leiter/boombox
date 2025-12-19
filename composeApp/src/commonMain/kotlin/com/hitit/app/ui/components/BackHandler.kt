package com.hitit.app.ui.components

import androidx.compose.runtime.Composable

@Suppress("EXPECT_ACTUAL_CLASSIFIERS_ARE_IN_BETA_WARNING")
@Composable
expect fun PlatformBackHandler(enabled: Boolean = true, onBack: () -> Unit)
