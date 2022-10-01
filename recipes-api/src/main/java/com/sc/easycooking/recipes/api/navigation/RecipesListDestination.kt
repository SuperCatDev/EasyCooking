package com.sc.easycooking.recipes.api.navigation

import com.sc.easycooking.navigation.EcNavigationDestination

object RecipesListDestination : EcNavigationDestination {
    override val route: String = "recipes_list_route"
    override val destination: String = "recipes_list_destination"
}