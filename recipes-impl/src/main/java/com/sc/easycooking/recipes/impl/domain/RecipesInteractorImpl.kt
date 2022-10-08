package com.sc.easycooking.recipes.impl.domain

import com.sc.easycooking.recipes.api.navigation.domain.RecipesInteractor
import com.sc.easycooking.recipes.api.navigation.models.RecipeModel
import com.sc.easycooking.recipes.impl.repository.RecipesRepository
import javax.inject.Inject

internal class RecipesInteractorImpl @Inject constructor(
    private val repository: RecipesRepository,
) : RecipesInteractor {
    override suspend fun getAllRecipes(): List<RecipeModel> {
        return repository.getAllRecipes()
    }
}