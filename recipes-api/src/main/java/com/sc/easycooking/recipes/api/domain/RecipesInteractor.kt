package com.sc.easycooking.recipes.api.domain

import androidx.paging.PagingData
import com.sc.easycooking.recipes.api.models.RecipeModel
import kotlinx.coroutines.flow.Flow

interface RecipesInteractor {
    fun observeAllRecipes(): Flow<PagingData<RecipeModel>>
    suspend fun deleteRecipesByIds(ids: Set<Int>)
}