package com.sc.easycooking.recipes.impl.presentation

import android.app.Application
import android.os.Parcelable
import android.util.Log
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.viewModelScope
import androidx.paging.PagingData
import androidx.paging.map
import com.sc.easycooking.recipes.api.domain.RecipesInteractor
import com.sc.easycooking.recipes.impl.presentation.mappers.toUiModelShort
import com.sc.easycooking.recipes.impl.presentation.models.RecipeUiModelShort
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.stateIn
import kotlinx.parcelize.Parcelize
import javax.inject.Inject

@HiltViewModel
internal class RecipesListViewModel @Inject constructor(
    private val savedStateHandle: SavedStateHandle,
    private val interactor: RecipesInteractor,
    application: Application,
) : AndroidViewModel(application) {

    private val selectedItems: StateFlow<SelectedItemsContainer> =
        savedStateHandle.getStateFlow(
            SelectedItemsContainer.CONTAINER_ID,
            SelectedItemsContainer(emptySet()),
        )

    fun observeSelectedItems(): StateFlow<Set<RecipeUiModelShort>> = selectedItems.map { it.set }
        .stateIn(viewModelScope, started = SharingStarted.Eagerly, emptySet())

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

    fun longClickedAt(item: RecipeUiModelShort) {
        val currentList = selectedItems.value.set.toMutableSet()

        if (currentList.contains(item)) {
            currentList.remove(item)
        } else {
            currentList.add(item)
        }

        savedStateHandle[SelectedItemsContainer.CONTAINER_ID] = SelectedItemsContainer(currentList)
    }

    @Parcelize
    private data class SelectedItemsContainer(val set: Set<RecipeUiModelShort>) : Parcelable {
        companion object {
            const val CONTAINER_ID = "selected_items"
        }
    }
}