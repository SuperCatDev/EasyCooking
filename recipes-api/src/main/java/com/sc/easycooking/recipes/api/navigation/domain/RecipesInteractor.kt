package com.sc.easycooking.recipes.api.navigation.domain

import com.sc.easycooking.recipes.api.navigation.models.RecipeModel

interface RecipesInteractor {
    suspend fun getAllRecipes(): List<RecipeModel>
}