package com.sc.easycooking.recipes.impl.presentation.models.details

import com.sc.easycooking.recipes.api.models.IngredientModel
import com.sc.easycooking.recipes.api.models.RecipeTag

internal data class ScreenContentState(
    val editMode: Boolean,
    val mutableState: MutableScreenContentState,
)

internal sealed class MutableScreenContentState {
    object None : MutableScreenContentState()
    object Loading : MutableScreenContentState()
    data class Content(val currentModel: CreateModel) : MutableScreenContentState()
    data class Error(val message: String) : MutableScreenContentState()
}

internal data class CreateModel(
    val id: Int?,
    val name: String?,
    val recipe: String?,
    val cookingTime: Long?,
    val category: RecipeCategoryScreenState,
    val tags: List<RecipeTag>,
    val ingredients: List<IngredientModel>,
)

internal data class RecipeCategoryScreenState(
    val text: String,
    val id: Int,
) {
    companion object {
        const val NO_ID = -1
    }
}