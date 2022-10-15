package com.sc.easycooking.recipes.impl.presentation.models

import com.sc.easycooking.recipes.api.navigation.models.RecipeTag

data class RecipeUiTag(
    val tag: RecipeTag,
    val name: String,
)