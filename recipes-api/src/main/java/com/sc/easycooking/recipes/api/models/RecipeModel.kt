package com.sc.easycooking.recipes.api.models

enum class RecipeCategory(val id: Int) {
    COLD_APPETIZER(0),
    HOT_APPETIZER(1),
    SOUP(2),
    SALAD(3),
    MAIN(4),
    DESERT(5),
    CHEESE(6),
}

enum class RecipeTag(val id: Int) {
    MEAT(0),
    FISH(1),
    SEAFOOD(2),
    VEGETARIAN(3),
    VEGETABLES(4),
    FRUITS(5),
    BAKERY(6),
}

data class RecipeModel(
    val id: Int,
    val name: String,
    val recipe: String,
    val creationDate: Long,
    val cookingTime: Long,
    val category: RecipeCategory,
    val tags: List<RecipeTag>,
    val ingredients: List<IngredientModel>,
)