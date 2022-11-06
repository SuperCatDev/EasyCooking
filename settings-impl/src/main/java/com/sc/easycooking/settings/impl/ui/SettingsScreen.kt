package com.sc.easycooking.settings.impl.ui

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import com.google.accompanist.systemuicontroller.rememberSystemUiController

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
    val systemUiController = rememberSystemUiController()
    systemUiController.setNavigationBarColor(
        color = MaterialTheme.colorScheme.background
    )

    Box(
        modifier = modifier
            .fillMaxSize()
    ) {
        Text(text = "Hello world!")
    }
}