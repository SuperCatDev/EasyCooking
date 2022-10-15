package com.sc.easycooking.recipes.impl.repository.mappers

import com.sc.easycooking.db.database.model.IngredientDbModel
import com.sc.easycooking.db.database.model.RecipeEntity
import com.sc.easycooking.recipes.api.navigation.models.IngredientModel
import com.sc.easycooking.recipes.api.navigation.models.QuantityType
import com.sc.easycooking.recipes.api.navigation.models.RecipeCategory
import com.sc.easycooking.recipes.api.navigation.models.RecipeModel
import com.sc.easycooking.recipes.api.navigation.models.RecipeTag

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
            ?: throw java.lang.IllegalArgumentException(
                "Unsupported RecipeCategory type in db model : $categoryId"
            ),
        tags = tagIds.map { tagId ->
            RecipeTag.values().find { it.id == tagId } ?: throw java.lang.IllegalArgumentException(
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
            ?: throw java.lang.IllegalArgumentException(
                "Unsupported quantity type in db model : $quantityType"
            ),
        amount = amount,
    )
}