package com.sc.easycooking.recipes.api.models

enum class QuantityType(val id: Int) {
    ML(0),
    LITRES(1),
    GRAMS(2),
    KG(3),
    AMOUNT(4),
}

data class IngredientModel(
    val name: String,
    val quantity: QuantityType,
    val amount: Int,
)