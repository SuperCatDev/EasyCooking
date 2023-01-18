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
    data class Content(val currentModel: CreateModel) : MutableScreenContentState()
    data class Error(val message: String) : MutableScreenContentState()
}

data class CreateModel(
    val id: Int?,
    val name: String?,
    val recipe: String?,
    val cookingTime: Long?,
    val category: RecipeCategory?,
    val tags: List<RecipeTag>,
    val ingredients: List<IngredientModel>,
) {
    companion object {
        val EMPTY = CreateModel(
            id = null,
            name = null,
            recipe = null,
            cookingTime = null,
            category = null,
            tags = emptyList(),
            ingredients = emptyList(),
        )


        fun fromRecipe(recipe: RecipeModel): CreateModel {
            return CreateModel(
                id = recipe.id,
                name = recipe.name,
                recipe = recipe.recipe,
                cookingTime = recipe.cookingTime,
                category = recipe.category,
                tags = recipe.tags,
                ingredients = recipe.ingredients,
            )
        }
    }
}