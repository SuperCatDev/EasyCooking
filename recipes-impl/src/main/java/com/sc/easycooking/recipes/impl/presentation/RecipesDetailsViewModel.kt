package com.sc.easycooking.recipes.impl.presentation

import android.app.Application
import android.content.Context
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.viewModelScope
import com.sc.easycooking.recipes.api.domain.RecipesInteractor
import com.sc.easycooking.recipes.api.models.RecipeCategory
import com.sc.easycooking.recipes.api.models.RecipeModel
import com.sc.easycooking.recipes.api.navigation.RecipeDetailsDestination
import com.sc.easycooking.recipes.impl.domain.FastAccessDataShare
import com.sc.easycooking.recipes.impl.domain.share_keys.SELECTED_ITEM_SHARE
import com.sc.easycooking.recipes.impl.presentation.mappers.RecipeCreateModelMapper
import com.sc.easycooking.recipes.impl.presentation.mappers.localizedName
import com.sc.easycooking.recipes.impl.presentation.models.details.CreateModel
import com.sc.easycooking.recipes.impl.presentation.models.details.MutableScreenContentState
import com.sc.easycooking.recipes.impl.presentation.models.details.RecipeCategoryScreenState
import com.sc.easycooking.recipes.impl.presentation.models.details.ScreenContentState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.debounce
import kotlinx.coroutines.flow.drop
import kotlinx.coroutines.flow.filter
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import javax.inject.Inject

@HiltViewModel
internal class RecipesDetailsViewModel @Inject constructor(
    private val interactor: RecipesInteractor,
    private val mapper: RecipeCreateModelMapper,
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

    private val categoriesToSelect: List<RecipeCategoryScreenState>

    val categoriesTexts: List<String>

    init {
        val categories = fillCategories(application)
        categoriesToSelect = categories.first
        categoriesTexts = categories.second

        if (editMode) {
            initEditMode()
        } else {
            initCreateMode()
        }

        viewModelScope.launch {
            screenContentState
                .drop(1)
                .debounce(600)
                .filter {
                    it.mutableState is MutableScreenContentState.Content
                }
                .map {
                    (it.mutableState as MutableScreenContentState.Content).currentModel
                }
                .filter { model ->
                    verifyModelIsReadyToSave(model)
                }
                .onEach { modelToSave ->
                    val id = interactor.updateOrSave(mapper.fromCreateToRecipeModel(modelToSave))

                    withContext(Dispatchers.Main) {
                        updateContentModel { modelToUpdate ->
                            modelToUpdate.copy(id = id)
                        }
                    }
                }
                .collect()
        }
    }

    fun observeScreenState(): StateFlow<ScreenContentState> = screenContentState

    fun timeChanged(time: String) {
        val timeLong = time.toLongOrNull()

        updateContentModel {
            it.copy(cookingTime = timeLong)
        }
    }

    fun nameChanged(newName: String) {
        updateContentModel {
            it.copy(name = newName)
        }
    }

    fun recipeChanged(newRecipe: String) {
        updateContentModel {
            it.copy(recipe = newRecipe)
        }
    }

    fun selectedCategory(index: Int) {
        updateContentModel {
            it.copy(category = categoriesToSelect[index])
        }
    }

    private fun initEditMode() {
        require(itemId != null)

        val modelFromFastShare =
            (fastShare.getItem(SELECTED_ITEM_SHARE) as? RecipeModel)?.takeIf { model -> model.id == itemId }

        if (modelFromFastShare != null) {
            updateMutableState {
                MutableScreenContentState.Content(
                    currentModel = mapper.fromRecipeToCreate(modelFromFastShare)
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
                            currentModel = mapper.fromRecipeToCreate(modelFromRepo)
                        )
                    }
                }
            }
        }
    }

    private fun initCreateMode() {
        updateMutableState {
            MutableScreenContentState.Content(
                currentModel = mapper.emptyCreateModel(),
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

    private fun verifyModelIsReadyToSave(model: CreateModel): Boolean {
        return model.name != null && model.recipe != null && model.category.id != RecipeCategoryScreenState.NO_ID
    }

    private fun fillCategories(context: Context): Pair<List<RecipeCategoryScreenState>, List<String>> {
        val categoriesToSelectMutable = mutableListOf<RecipeCategoryScreenState>()
        val categoriesTextsMutable = mutableListOf<String>()

        RecipeCategory.values().forEach {
            val localizedText = it.localizedName(context)
            categoriesToSelectMutable.add(RecipeCategoryScreenState(localizedText, it.id))
            categoriesTextsMutable.add(localizedText)
        }

        return categoriesToSelectMutable to categoriesTextsMutable
    }
}


