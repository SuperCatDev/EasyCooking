package com.sc.easycooking.recipes.impl.presentation

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.paging.PagingData
import androidx.paging.map
import com.sc.easycooking.recipes.api.navigation.domain.RecipesInteractor
import com.sc.easycooking.recipes.api.navigation.models.RecipeModel
import com.sc.easycooking.recipes.impl.presentation.mappers.toUiModelShort
import com.sc.easycooking.recipes.impl.presentation.models.RecipeUiModelShort
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject

@HiltViewModel
internal class RecipesListViewModel @Inject constructor(
    savedStateHandle: SavedStateHandle,
    private val interactor: RecipesInteractor,
) : ViewModel() {
    fun observeAllRecipes(): Flow<PagingData<RecipeUiModelShort>> {
        return interactor.observeAllRecipes()
            .map {
                it.map(RecipeModel::toUiModelShort)
            }
    }
}