package com.sc.easycooking.recipes.impl.presentation.models.details

import com.sc.easycooking.recipes.api.models.IngredientModel
import com.sc.easycooking.recipes.api.models.RecipeCategory
import com.sc.easycooking.recipes.api.models.RecipeModel
import com.sc.easycooking.recipes.api.models.RecipeTag

data class ScreenContentState(
    val editMode: Boolean,
    val mutableState: MutableScreenContentState,
)

sealed class MutableScreenContentState {
    object None : MutableScreenContentState()
    object Loading : MutableScreenContentState()
    data class Content(val state: ContentState) : MutableScreenContentState()
    data class Error(val message: String) : MutableScreenContentState()
}

sealed class ContentState {
    data class EditContent(val currentModel: RecipeModel) : ContentState()
    data class CreateContent(val createModel: CreateModel) : ContentState()
}

data class CreateModel(
    val name: String?,
    val recipe: String?,
    val cookingTime: Long?,
    val category: RecipeCategory?,
    val tags: List<RecipeTag>,
    val ingredients: List<IngredientModel>,
) {
    companion object {
        val EMPTY = CreateModel(
            name = null,
            recipe = null,
            cookingTime = null,
            category = null,
            tags = emptyList(),
            ingredients = emptyList(),
        )
    }
}