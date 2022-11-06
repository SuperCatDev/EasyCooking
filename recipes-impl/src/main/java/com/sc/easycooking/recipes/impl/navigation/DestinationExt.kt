@file:OptIn(ExperimentalAnimationApi::class)

package com.sc.easycooking.recipes.impl.navigation

import androidx.compose.animation.ExperimentalAnimationApi
import androidx.navigation.NavGraphBuilder
import com.google.accompanist.navigation.animation.composable
import com.sc.easycooking.recipes.api.navigation.RecipesListDestination
import com.sc.easycooking.recipes.impl.ui.RecipesListRoute

fun NavGraphBuilder.recipesListGraph(
    navigateToSettings: () -> Unit,
) {
    composable(route = RecipesListDestination.route) {
        RecipesListRoute(
            navigateToSettings = navigateToSettings,
        )
    }
}