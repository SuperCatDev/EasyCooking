package com.sc.easycooking.recipes.impl.presentation.mappers

import android.app.Application
import com.sc.easycooking.recipes.api.models.RecipeCategory
import com.sc.easycooking.recipes.api.models.RecipeModel
import com.sc.easycooking.recipes.impl.R
import com.sc.easycooking.recipes.impl.presentation.models.details.CreateModel
import com.sc.easycooking.recipes.impl.presentation.models.details.RecipeCategoryScreenState
import javax.inject.Inject

internal class RecipeCreateModelMapper @Inject constructor(private val application: Application) {
    fun emptyCreateModel(): CreateModel {
        return CreateModel(
            id = null,
            name = null,
            recipe = null,
            cookingTime = null,
            category = RecipeCategoryScreenState(
                text = application.getString(R.string.category_no),
                id = RecipeCategoryScreenState.NO_ID,
            ),
            tags = emptyList(),
            ingredients = emptyList(),
        )
    }

    fun fromRecipeToCreate(recipe: RecipeModel): CreateModel {
        return CreateModel(
            id = recipe.id,
            name = recipe.name,
            recipe = recipe.recipe,
            cookingTime = recipe.cookingTime,
            category = RecipeCategoryScreenState(
                recipe.category.localizedName(application),
                recipe.category.id,
            ),
            tags = recipe.tags,
            ingredients = recipe.ingredients,
        )
    }

    fun fromCreateToRecipeModel(model: CreateModel): RecipeModel {
        check(model.name != null)
        check(model.recipe != null)

        val selectedCategory = RecipeCategory.values().find {
            it.id == model.category.id
        }

        check(selectedCategory != null)

        return RecipeModel(
            id = model.id ?: 0,
            name = model.name,
            recipe = model.recipe,
            cookingTime = model.cookingTime ?: 0,
            category = selectedCategory,
            tags = model.tags,
            ingredients = model.ingredients,
            creationDate = System.currentTimeMillis()
        )
    }
}