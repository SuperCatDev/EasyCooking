package com.sc.easycooking.recipes.impl.presentation.mappers

import com.sc.easycooking.recipes.api.models.RecipeCategory
import com.sc.easycooking.recipes.api.models.RecipeModel
import com.sc.easycooking.recipes.impl.presentation.models.details.CreateModel
import javax.inject.Inject

internal class RecipeCreateModelMapper @Inject constructor() {
    fun fromRecipeToCreate(recipe: RecipeModel): CreateModel {
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

    fun fromCreateToRecipeModel(model: CreateModel): RecipeModel {
        check(model.name != null)
        check(model.recipe != null)

        return RecipeModel(
            id = model.id ?: 0,
            name = model.name,
            recipe = model.recipe,
            cookingTime = model.cookingTime ?: 0,
            // todo with category
            category = model.category ?: RecipeCategory.MAIN,
            tags = model.tags,
            ingredients = model.ingredients,
            creationDate = System.currentTimeMillis()
        )
    }
}