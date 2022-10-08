package com.sc.easycooking.recipes.impl.di

import com.sc.easycooking.recipes.api.navigation.domain.RecipesInteractor
import com.sc.easycooking.recipes.impl.domain.RecipesInteractorImpl
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@Module
@InstallIn(SingletonComponent::class)
internal interface DomainModule {
    @Binds
    fun bindsRecipeInteractor(
        interactor: RecipesInteractorImpl,
    ): RecipesInteractor
}