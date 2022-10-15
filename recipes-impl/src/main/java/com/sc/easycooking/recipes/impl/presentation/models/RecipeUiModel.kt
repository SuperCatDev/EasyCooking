package com.sc.easycooking.recipes.impl.presentation.models

internal data class RecipeUiModel(
    val id: Int,
    val name: String,
    val recipe: String,
    val creationDate: Long,
    val cookingTime: Long,
    val category: String,
    val tags: List<RecipeUiTag>,
    val ingredients: List<IngredientUiModel>,
)

internal data class RecipeUiModelShort(
    val name: String,
    val category: String,
)