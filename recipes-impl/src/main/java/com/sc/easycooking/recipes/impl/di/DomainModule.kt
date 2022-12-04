package com.sc.easycooking.recipes.impl.di

import com.sc.easycooking.recipes.api.domain.RecipesInteractor
import com.sc.easycooking.recipes.impl.domain.FastAccessDataShare
import com.sc.easycooking.recipes.impl.domain.FastAccessDataShareImpl
import com.sc.easycooking.recipes.impl.domain.RecipesInteractorImpl
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
internal interface DomainModule {
    @Binds
    fun bindsRecipeInteractor(
        interactor: RecipesInteractorImpl,
    ): RecipesInteractor

    @Binds
    @Singleton
    fun bindsFastDataShareInteractor(
        fastShare: FastAccessDataShareImpl,
    ): FastAccessDataShare
}