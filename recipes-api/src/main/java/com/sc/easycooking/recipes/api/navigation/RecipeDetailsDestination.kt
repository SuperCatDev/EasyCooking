package com.sc.easycooking.recipes.api.navigation

import com.sc.easycooking.navigation.EcNavigationDestination

data class RecipeDetailsDestination(
    val id: Int?,
    val edit: Boolean,
) : EcNavigationDestination {
    override val route: String
        get() {
            return if (id != null) {
                "recipe_details/$edit?$ID_ARG=$id}"
            } else {
                "recipe_details/$edit"
            }
        }
    override val destination: String = "recipe_details_destination"

    companion object {
        const val ID_ARG = "id"
        const val EDIT_ARG = "edit"
        const val NAV_COMMAND = "recipe_details/{$EDIT_ARG}?$ID_ARG={$ID_ARG}}"
    }
}