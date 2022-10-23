package com.sc.easycooking.recipes.impl.repository.mappers

import com.sc.easycooking.db.database.model.IngredientDbModel
import com.sc.easycooking.db.database.model.RecipeEntity
import com.sc.easycooking.recipes.api.models.IngredientModel
import com.sc.easycooking.recipes.api.models.QuantityType
import com.sc.easycooking.recipes.api.models.RecipeCategory
import com.sc.easycooking.recipes.api.models.RecipeModel
import com.sc.easycooking.recipes.api.models.RecipeTag
import java.lang.IllegalArgumentException

fun RecipeModel.toDbModel(): RecipeEntity {
    return RecipeEntity(
        name = name,
        recipe = recipe,
        creationDate = creationDate,
        cookingTime = cookingTime,
        categoryId = category.id,
        tagIds = tags.map { it.id },
        ingredients = ingredients.map(IngredientModel::toDbModel)
    )
}

fun RecipeEntity.toDomainModel(): RecipeModel {
    return RecipeModel(
        id = id,
        name = name,
        recipe = recipe,
        creationDate = creationDate,
        cookingTime = cookingTime,
        category = RecipeCategory.values().find { it.id == categoryId }
            ?: throw IllegalArgumentException(
                "Unsupported RecipeCategory type in db model : $categoryId"
            ),
        tags = tagIds.map { tagId ->
            RecipeTag.values().find { it.id == tagId } ?: throw IllegalArgumentException(
                "Unsupported RecipeTag value in db model : $tagId"
            )
        },
        ingredients = ingredients.map(IngredientDbModel::toDomainModel),
    )
}

fun IngredientModel.toDbModel(): IngredientDbModel {
    return IngredientDbModel(
        name = name,
        quantityType = quantity.id,
        amount = amount,
    )
}

fun IngredientDbModel.toDomainModel(): IngredientModel {
    return IngredientModel(
        name = name,
        quantity = QuantityType.values().find { it.id == quantityType }
            ?: throw IllegalArgumentException(
                "Unsupported quantity type in db model : $quantityType"
            ),
        amount = amount,
    )
}