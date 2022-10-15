package com.sc.easycooking.recipes.api.navigation.domain

import androidx.paging.PagingData
import com.sc.easycooking.recipes.api.navigation.models.RecipeModel
import kotlinx.coroutines.flow.Flow

interface RecipesInteractor {
    fun observeAllRecipes(): Flow<PagingData<RecipeModel>>
}