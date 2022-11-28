@file:OptIn(ExperimentalAnimationApi::class)

package com.sc.easycooking.navigation

import androidx.compose.animation.ExperimentalAnimationApi
import androidx.compose.animation.core.tween
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.NavHostController
import com.google.accompanist.navigation.animation.AnimatedNavHost
import com.sc.easycooking.recipes.api.navigation.RecipeDetailsDestination
import com.sc.easycooking.recipes.api.navigation.RecipesListDestination
import com.sc.easycooking.recipes.impl.navigation.recipesListGraph
import com.sc.easycooking.settings.api.navigation.SettingsDestination
import com.sc.easycooking.settings.impl.navigation.settingsGraph

/**
 * Top-level navigation graph. Navigation is organized as explained at
 * https://d.android.com/jetpack/compose/nav-adaptive
 *
 * The navigation graph defined in this file defines the different top level routes. Navigation
 * within each route is handled using state and Back Handlers.
 */
@Composable
fun EcNavHost(
    navController: NavHostController,
    onNavigateToDestination: (EcNavigationDestination, String?) -> Unit,
    onBackClick: () -> Unit,
    modifier: Modifier = Modifier,
) {
    AnimatedNavHost(
        navController = navController,
        startDestination = RecipesListDestination.route,
        enterTransition = { fadeIn(animationSpec = tween(300)) },
        exitTransition = { fadeOut(animationSpec = tween(300)) },
        modifier = modifier,
    ) {
        recipesListGraph(
            navigateToSettings = {
                onNavigateToDestination(SettingsDestination, null)
            },
            navigateToDetails = { id: Int?, edit: Boolean ->
                onNavigateToDestination(RecipeDetailsDestination(id, edit), null)
            },
            onBackClick = onBackClick,
        )
        settingsGraph(onBackClick)
    }
}