package com.sc.easycooking.recipes.impl.repository

import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import com.sc.easycooking.db.database.dao.RecipesDao
import com.sc.easycooking.db.database.model.RecipeEntity
import com.sc.easycooking.recipes.api.navigation.models.RecipeModel
import com.sc.easycooking.recipes.impl.repository.mappers.toDomainModel
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.launch
import javax.inject.Inject

internal class RecipesRepository @Inject constructor(private val recipesDao: RecipesDao) {
    init {
        GlobalScope.launch {
            delay(5000L)
            recipesDao.addRecipe(
                RecipeEntity(
                    name = "Test recipe :)",
                    recipe = "heje",
                    creationDate = System.currentTimeMillis(),
                    cookingTime = 5,
                    categoryId = 1,
                    tagIds = emptyList(),
                    ingredients = emptyList()
                )
            )
        }
    }

    fun observeAllRecipes(): Flow<PagingData<RecipeModel>> {
        return Pager(
            PagingConfig(10),
            null,
            recipesDao.observeAllRecipes().map {
                it.toDomainModel()
            }
                .asPagingSourceFactory()
        ).flow
    }
}