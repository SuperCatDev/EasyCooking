package com.sc.easycooking.recipes.impl.presentation

import android.app.Application
import android.os.Parcelable
import android.util.Log
import android.util.LruCache
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.viewModelScope
import androidx.paging.PagingData
import androidx.paging.cachedIn
import androidx.paging.map
import com.sc.easycooking.recipes.api.domain.RecipesInteractor
import com.sc.easycooking.recipes.api.models.RecipeModel
import com.sc.easycooking.recipes.impl.domain.FastAccessDataShare
import com.sc.easycooking.recipes.impl.domain.share_keys.SELECTED_ITEM_SHARE
import com.sc.easycooking.recipes.impl.presentation.mappers.toUiModelShort
import com.sc.easycooking.recipes.impl.presentation.models.RecipeUiModelShort
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch
import kotlinx.parcelize.Parcelize
import javax.inject.Inject

@HiltViewModel
internal class RecipesListViewModel @Inject constructor(
    private val savedStateHandle: SavedStateHandle,
    private val interactor: RecipesInteractor,
    private val fastShare: FastAccessDataShare,
    application: Application,
) : AndroidViewModel(application) {

    init {
        Log.e("VVV", "create RecipesListViewModel: ${hashCode()}")
    }

    private val lruCache = LruCache<Int, RecipeModel>(100)

    private val selectedItems: StateFlow<SelectedItemsContainer> =
        savedStateHandle.getStateFlow(
            SelectedItemsContainer.CONTAINER_ID,
            SelectedItemsContainer(emptySet()),
        )

    fun observeSelectedItems(): StateFlow<Set<RecipeUiModelShort>> = selectedItems.map { it.set }
        .stateIn(viewModelScope, started = SharingStarted.WhileSubscribed(5000), emptySet())

    private val recipesItems = interactor.observeAllRecipes()
        .map { pagingData ->
            pagingData.map { model ->
                lruCache.put(model.id, model)
                model.toUiModelShort(getApplication())
            }
        }
        .cachedIn(viewModelScope)
        .stateIn(
            viewModelScope,
            started = SharingStarted.Eagerly,
            PagingData.empty(),
        )

    fun observeAllRecipes(): Flow<PagingData<RecipeUiModelShort>> {
        return recipesItems
    }

    fun clearSelection() {
        savedStateHandle[SelectedItemsContainer.CONTAINER_ID] = SelectedItemsContainer(emptySet())
    }

    fun deleteSelected() {
        viewModelScope.launch {
            val idsToDelete = selectedItems.value.set.mapTo(mutableSetOf(), RecipeUiModelShort::id)
            interactor.deleteRecipesByIds(idsToDelete)
        }

        clearSelection()
    }

    fun clickedAt(item: RecipeUiModelShort) {
        lruCache.get(item.id)?.let {
            fastShare.putItem(SELECTED_ITEM_SHARE, it)
        }
    }

    fun longClickedAt(item: RecipeUiModelShort) {
        val currentSet = selectedItems.value.set.toMutableSet()

        if (currentSet.contains(item)) {
            currentSet.remove(item)
        } else {
            currentSet.add(item)
        }

        savedStateHandle[SelectedItemsContainer.CONTAINER_ID] = SelectedItemsContainer(currentSet)
    }

    @Parcelize
    private data class SelectedItemsContainer(val set: Set<RecipeUiModelShort>) : Parcelable {
        companion object {
            const val CONTAINER_ID = "selected_items"
        }
    }
}