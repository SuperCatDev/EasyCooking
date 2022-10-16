package com.sc.easycooking.recipes.impl.presentation.mappers

import com.sc.easycooking.recipes.api.navigation.models.RecipeModel
import com.sc.easycooking.recipes.impl.presentation.models.IngredientUiModel
import com.sc.easycooking.recipes.impl.presentation.models.RecipeUiModelShort

internal fun RecipeModel.toUiModelShort(): RecipeUiModelShort {
    return RecipeUiModelShort(
        name = name,
        category = category.name,
        ingredients = ingredients.map {
            IngredientUiModel(
                name = it.name,
                amount = it.amount,
                quantity = it.quantity.name,
            )
        }
    )
}