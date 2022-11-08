package com.sc.easycooking.settings.impl.ui

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.asPaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.systemBars
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import com.sc.easycooking.view_ext.insets.WrapWithColoredSystemBars

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
    WrapWithColoredSystemBars(
        modifier = modifier,
        navBarColor = null,
    ) { innerModifier ->
        Box(
            modifier = innerModifier
                .fillMaxSize()
                .padding(WindowInsets.systemBars.asPaddingValues())
        ) {
            Text(text = "Hello world!")
        }
    }
}