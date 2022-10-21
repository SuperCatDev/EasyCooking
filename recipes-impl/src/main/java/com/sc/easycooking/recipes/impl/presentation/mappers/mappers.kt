package com.sc.easycooking.recipes.impl.presentation.mappers

import android.content.Context
import com.sc.easycooking.recipes.api.models.QuantityType
import com.sc.easycooking.recipes.api.models.RecipeModel
import com.sc.easycooking.recipes.impl.R
import com.sc.easycooking.recipes.impl.presentation.models.IngredientUiModel
import com.sc.easycooking.recipes.impl.presentation.models.RecipeUiModelShort

internal fun RecipeModel.toUiModelShort(context: Context): RecipeUiModelShort {
    return RecipeUiModelShort(
        name = name,
        category = category.name,
        ingredients = ingredients.map {
            IngredientUiModel(
                name = it.name,
                amount = it.amount,
                quantity = it.quantity.getQuantityLocalizedName(context),
            )
        }
    )
}

private fun QuantityType.getQuantityLocalizedName(context: Context): String {
    val stringRes = when (this) {
        QuantityType.ML -> {
            R.string.quantity_ml
        }
        QuantityType.LITRES -> {
            R.string.quantity_l
        }
        QuantityType.GRAMS -> {
            R.string.quantity_g
        }
        QuantityType.KG -> {
            R.string.quantity_kg
        }
        QuantityType.AMOUNT -> {
            null
        }
    }

    return stringRes?.let {
        context.getString(it)
    } ?: ""
}