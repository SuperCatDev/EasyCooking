@file:OptIn(ExperimentalAnimationApi::class)

package com.sc.easycooking.recipes.impl.navigation

import androidx.compose.animation.ExperimentalAnimationApi
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
            navArgument("id") {
                type = NavType.IntType
            },
            navArgument("edit") {
                type = NavType.BoolType
            },
        )
    ) { backStackEntry ->
        val id = backStackEntry.arguments?.getInt(RecipeDetailsDestination.ID_ARG)
        val edit = backStackEntry.arguments?.getBoolean(RecipeDetailsDestination.EDIT_ARG) ?: false

        RecipeDetailsRoute(
            id = id,
            edit = edit,
            onBackClick = onBackClick,
        )
    }
}