package com.sc.easycooking.recipes.impl.navigation

import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import com.sc.easycooking.recipes.api.navigation.RecipesListDestination
import com.sc.easycooking.recipes.impl.ui.RecipesListRoute

fun NavGraphBuilder.recipesListGraph() {
    composable(route = RecipesListDestination.route) {
        RecipesListRoute()
    }
}