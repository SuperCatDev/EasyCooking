package com.sc.easycooking.recipes.impl.presentation

import android.app.Application
import android.util.Log
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.SavedStateHandle
import androidx.paging.PagingData
import androidx.paging.map
import com.sc.easycooking.recipes.api.domain.RecipesInteractor
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
    application: Application,
) : AndroidViewModel(application) {
    fun observeAllRecipes(): Flow<PagingData<RecipeUiModelShort>> {
        return interactor.observeAllRecipes()
            .map { pagingData ->
                pagingData.map { model ->
                    model.toUiModelShort(getApplication())
                }
            }
    }

    fun addClicked() {

    }

    fun clickedAt(item: RecipeUiModelShort) {
        Log.e("VVV", "clicked on: $item")
    }
}