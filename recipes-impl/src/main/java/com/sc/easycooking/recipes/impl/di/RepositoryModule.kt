package com.sc.easycooking.recipes.impl.di

import com.sc.easycooking.db.database.dao.RecipesDao
import com.sc.easycooking.recipes.impl.repository.RecipesRepository
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@Module
@InstallIn(SingletonComponent::class)
internal object RepositoryModule {
    @Provides
    fun providesRecipeRepository(
        dao: RecipesDao,
    ): RecipesRepository = RecipesRepository(dao)
}