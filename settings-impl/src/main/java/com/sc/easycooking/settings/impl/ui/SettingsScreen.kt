package com.sc.easycooking.settings.impl.ui

import androidx.compose.foundation.layout.Box
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier

@Composable
internal fun SettingsScreenRoute(
    modifier: Modifier = Modifier,
    onBackClick: () -> Unit,
) {
    SettingsScreen(modifier, onBackClick)
}

@Composable
internal fun SettingsScreen(
    modifier: Modifier = Modifier,
    onBackClick: () -> Unit,
) {
    Box(modifier = modifier) {
        Text(text = "Hello world!")
    }
}