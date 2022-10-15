package com.sc.easycooking.recipes.impl.domain

import androidx.paging.PagingData
import com.sc.easycooking.recipes.api.navigation.domain.RecipesInteractor
import com.sc.easycooking.recipes.api.navigation.models.RecipeModel
import com.sc.easycooking.recipes.impl.repository.RecipesRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

internal class RecipesInteractorImpl @Inject constructor(
    private val repository: RecipesRepository,
) : RecipesInteractor {
    override fun observeAllRecipes(): Flow<PagingData<RecipeModel>> {
        return repository.observeAllRecipes()
    }
}