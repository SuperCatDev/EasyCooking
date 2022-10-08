package com.sc.easycooking.recipes.impl.repository

import com.sc.easycooking.db.database.dao.RecipesDao
import com.sc.easycooking.recipes.api.navigation.models.RecipeModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import javax.inject.Inject

internal class RecipesRepository @Inject constructor(private val recipesDao: RecipesDao) {
    suspend fun getAllRecipes(): List<RecipeModel> = withContext(Dispatchers.IO) {
        emptyList()
    }
}