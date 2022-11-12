package com.sc.easycooking.ui

import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.foundation.layout.ExperimentalLayoutApi
import androidx.compose.foundation.layout.consumedWindowInsets
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.State
import androidx.compose.ui.Modifier
import com.sc.easycooking.navigation.EcNavHost
import com.sc.easycooking.settings.api.models.DisplaySettings
import com.sc.easycooking.settings.api.models.ThemeSetting
import com.sc.easycooking.ui.theme.EasyCookingTheme

@OptIn(ExperimentalMaterial3Api::class, ExperimentalLayoutApi::class)
@Composable
fun EcApp(
    appState: EcAppState = rememberEcAppState(),
    displaySettings: State<DisplaySettings>
) {
    EasyCookingTheme(
        darkTheme = when (displaySettings.value.themeSetting) {
            ThemeSetting.DEFAULT -> isSystemInDarkTheme()
            ThemeSetting.LIGHT -> false
            ThemeSetting.DARK -> true
        },
        dynamicColor = displaySettings.value.materialYouEnabled,
    ) {
        Scaffold(
            modifier = Modifier.fillMaxSize(),
            containerColor = MaterialTheme.colorScheme.background
        ) { padding ->
            EcNavHost(
                navController = appState.navController,
                modifier = Modifier
                    .consumedWindowInsets(padding),
                onNavigateToDestination = { destination, route ->
                    appState.navigate(destination, route)
                },
                onBackClick = { appState.onBackClick() },
            )
        }
    }

}