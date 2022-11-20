package com.sc.easycooking.recipes.impl.presentation.models

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
internal data class RecipeUiModel(
    val id: Int,
    val name: String,
    val recipe: String,
    val creationDate: Long,
    val cookingTime: Long,
    val category: String,
    val tags: List<RecipeUiTag>,
    val ingredients: List<IngredientUiModel>,
) : Parcelable

@Parcelize
internal data class RecipeUiModelShort(
    val id: Int,
    val name: String,
    val category: String,
    val tags: List<RecipeUiTag>,
    val ingredients: List<IngredientUiModel>,
): Parcelable