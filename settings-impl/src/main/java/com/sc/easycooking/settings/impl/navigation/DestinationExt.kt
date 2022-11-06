@file:OptIn(ExperimentalAnimationApi::class)

package com.sc.easycooking.settings.impl.navigation

import androidx.compose.animation.ExperimentalAnimationApi
import androidx.compose.animation.slideInHorizontally
import androidx.compose.animation.slideOutHorizontally
import androidx.navigation.NavGraphBuilder
import com.google.accompanist.navigation.animation.composable
import com.sc.easycooking.settings.api.navigation.SettingsDestination
import com.sc.easycooking.settings.impl.ui.SettingsScreenRoute

fun NavGraphBuilder.settingsGraph(
    onBackClick: () -> Unit,
) {
    composable(
        route = SettingsDestination.route,
        enterTransition = {
            slideInHorizontally(initialOffsetX = { fullWidth ->
                fullWidth
            })
        },
        exitTransition = {
            slideOutHorizontally(targetOffsetX = { fullWidth ->
                fullWidth
            })
        },
    ) {
        SettingsScreenRoute(onBackClick = onBackClick)
    }
}