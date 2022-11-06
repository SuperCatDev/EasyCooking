package com.sc.easycooking.settings.impl.navigation

import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import com.sc.easycooking.settings.api.navigation.SettingsDestination
import com.sc.easycooking.settings.impl.ui.SettingsScreenRoute

fun NavGraphBuilder.settingsGraph(
    onBackClick: () -> Unit,
) {
    composable(route = SettingsDestination.route) {
        SettingsScreenRoute(onBackClick = onBackClick)
    }
}