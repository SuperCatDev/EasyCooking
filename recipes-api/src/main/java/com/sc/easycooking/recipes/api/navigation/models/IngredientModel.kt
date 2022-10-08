package com.sc.easycooking.recipes.api.navigation.models

@JvmInline
value class IngredientId(val id: Int)

enum class QuantityType(val id: Int) {
    ML(0),
    LITRES(1),
    GRAMS(2),
    KG(3),
    AMOUNT(4),
}

data class IngredientModel(
    val id: IngredientId,
    val name: String,
    val quantity: QuantityType,
)