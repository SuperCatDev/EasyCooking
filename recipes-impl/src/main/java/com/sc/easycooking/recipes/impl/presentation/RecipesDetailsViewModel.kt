package com.sc.easycooking.recipes.impl.presentation

import android.app.Application
import android.util.Log
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.viewModelScope
import com.sc.easycooking.recipes.api.domain.RecipesInteractor
import com.sc.easycooking.recipes.api.models.RecipeModel
import com.sc.easycooking.recipes.api.navigation.RecipeDetailsDestination
import com.sc.easycooking.recipes.impl.domain.FastAccessDataShare
import com.sc.easycooking.recipes.impl.domain.share_keys.SELECTED_ITEM_SHARE
import com.sc.easycooking.recipes.impl.presentation.models.details.CreateModel
import com.sc.easycooking.recipes.impl.presentation.models.details.MutableScreenContentState
import com.sc.easycooking.recipes.impl.presentation.models.details.ScreenContentState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.debounce
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
internal class RecipesDetailsViewModel @Inject constructor(
    private val interactor: RecipesInteractor,
    private val savedStateHandle: SavedStateHandle,
    private val fastShare: FastAccessDataShare,
    application: Application,
) : AndroidViewModel(application) {

    private val itemId: Int? =
        savedStateHandle.get<String>(RecipeDetailsDestination.ID_ARG)?.toIntOrNull()
    private val editMode: Boolean = savedStateHandle.get<Boolean>(RecipeDetailsDestination.EDIT_ARG)
        ?: throw IllegalArgumentException("RecipesDetailsViewModel requires RecipeDetailsDestination.EDIT_ARG")

    private val screenContentState = MutableStateFlow(
        ScreenContentState(
            editMode = editMode,
            mutableState = MutableScreenContentState.None,
        )
    )

    init {
        if (editMode) {
            initEditMode()
        } else {
            initCreateMode()
        }

        viewModelScope.launch {
            screenContentState
                .debounce(300)
                .onEach {
                    Log.e("VVV", "State update: $it")
                    delay(1000)
                }
                .collect()
        }
    }

    fun observeScreenState(): StateFlow<ScreenContentState> = screenContentState

    fun recipeChanged(newRecipe: String) {
        updateContentModel {
            it.copy(recipe = newRecipe)
        }
    }

    private fun initEditMode() {
        require(itemId != null)

        val modelFromFastShare =
            (fastShare.getItem(SELECTED_ITEM_SHARE) as? RecipeModel)?.takeIf { model -> model.id == itemId }

        if (modelFromFastShare != null) {
            updateMutableState {
                MutableScreenContentState.Content(
                    currentModel = CreateModel.fromRecipe(modelFromFastShare)
                )
            }
        } else {
            viewModelScope.launch {
                screenContentState.value = screenContentState.value.copy(
                    mutableState = MutableScreenContentState.Loading
                )
                val modelFromRepo = interactor.getRecipeForId(itemId)

                if (modelFromRepo == null) {
                    updateMutableState {
                        MutableScreenContentState.Error("Something went wrong!")
                    }
                } else {
                    updateMutableState {
                        MutableScreenContentState.Content(
                            currentModel = CreateModel.fromRecipe(modelFromRepo)
                        )
                    }
                }
            }
        }
    }

    private fun initCreateMode() {
        updateMutableState {
            MutableScreenContentState.Content(
                currentModel = CreateModel.EMPTY,
            )
        }
    }

    private inline fun updateMutableState(change: (oldState: MutableScreenContentState) -> MutableScreenContentState) {
        screenContentState.value = screenContentState.value.copy(
            mutableState = change(screenContentState.value.mutableState)
        )
    }

    private inline fun updateContentModel(change: (model: CreateModel) -> CreateModel) {
        val oldContent = screenContentState.value.mutableState

        if (oldContent is MutableScreenContentState.Content) {
            screenContentState.value = screenContentState.value.copy(
                mutableState = oldContent.copy(currentModel = change(oldContent.currentModel))
            )
        }
    }

    private inline fun updateContentState(change: (content: MutableScreenContentState.Content) -> MutableScreenContentState.Content) {
        val oldContent = screenContentState.value.mutableState

        if (oldContent is MutableScreenContentState.Content) {
            screenContentState.value = screenContentState.value.copy(
                mutableState = change(oldContent)
            )
        }
    }
}


