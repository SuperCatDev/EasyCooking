package com.sc.easycooking.navigation

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import com.sc.easycooking.recipes.api.navigation.RecipesListDestination
import com.sc.easycooking.recipes.impl.navigation.recipesListGraph

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
    //onNavigateToDestination: (EcNavigationDestination, String) -> Unit,
    //onBackClick: () -> Unit,
    modifier: Modifier = Modifier,
) {
    NavHost(
        navController = navController,
        startDestination = RecipesListDestination.route,
        modifier = modifier,
    ) {
        recipesListGraph()
    }
}