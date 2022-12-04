@file:OptIn(ExperimentalAnimationApi::class)

package com.sc.easycooking.recipes.impl.navigation

import androidx.compose.animation.ExperimentalAnimationApi
import androidx.compose.animation.core.tween
import androidx.compose.animation.fadeIn
import androidx.compose.animation.scaleIn
import androidx.compose.animation.scaleOut
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavType
import androidx.navigation.navArgument
import com.google.accompanist.navigation.animation.composable
import com.sc.easycooking.recipes.api.navigation.RecipeDetailsDestination
import com.sc.easycooking.recipes.api.navigation.RecipesListDestination
import com.sc.easycooking.recipes.impl.ui.RecipeDetailsRoute
import com.sc.easycooking.recipes.impl.ui.RecipesListRoute

fun NavGraphBuilder.recipesListGraph(
    navigateToSettings: () -> Unit,
    navigateToDetails: (id: Int?, edit: Boolean) -> Unit,
    onBackClick: () -> Unit,
) {
    composable(route = RecipesListDestination.route) {
        RecipesListRoute(
            navigateToSettings = navigateToSettings,
            navigateToDetails = navigateToDetails,
        )
    }
    composable(
        route = RecipeDetailsDestination.NAV_COMMAND,
        arguments = listOf(
            navArgument(RecipeDetailsDestination.ID_ARG) {
                nullable = true
            },
            navArgument(RecipeDetailsDestination.EDIT_ARG) {
                type = NavType.BoolType
            },
        ),
        enterTransition = {
            fadeIn() + scaleIn(tween(300), initialScale = 0.75f)
        },
        exitTransition = {
            scaleOut(tween(300), targetScale = 0.75f)
        },
    ) {
        RecipeDetailsRoute(
            onBackClick = onBackClick,
        )
    }
}