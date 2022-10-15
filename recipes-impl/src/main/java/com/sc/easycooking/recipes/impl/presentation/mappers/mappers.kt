package com.sc.easycooking.recipes.impl.presentation.mappers

import com.sc.easycooking.recipes.api.navigation.models.RecipeModel
import com.sc.easycooking.recipes.impl.presentation.models.RecipeUiModelShort

internal fun RecipeModel.toUiModelShort(): RecipeUiModelShort {
    return RecipeUiModelShort(
        name = name,
        category = category.name,
    )
}