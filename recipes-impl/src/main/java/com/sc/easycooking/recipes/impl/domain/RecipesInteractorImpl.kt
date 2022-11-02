package com.sc.easycooking.recipes.impl.domain

import androidx.paging.PagingData
import com.sc.easycooking.recipes.api.domain.RecipesInteractor
import com.sc.easycooking.recipes.api.models.RecipeModel
import com.sc.easycooking.recipes.impl.repository.RecipesRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

internal class RecipesInteractorImpl @Inject constructor(
    private val repository: RecipesRepository,
) : RecipesInteractor {
    override fun observeAllRecipes(): Flow<PagingData<RecipeModel>> {
        return repository.observeAllRecipes()
    }

    override suspend fun deleteRecipesByIds(ids: Set<Int>) {
        repository.deleteRecipesByIds(ids)
    }
}