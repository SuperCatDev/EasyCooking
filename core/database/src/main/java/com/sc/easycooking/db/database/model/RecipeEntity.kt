package com.sc.easycooking.db.database.model

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.Index
import androidx.room.PrimaryKey

enum class RecipeDbCategory(val id: Int) {
    COLD_APPETIZER(0),
    HOT_APPETIZER(1),
    SOUP(2),
    SALAD(3),
    MAIN(4),
    DESERT(5),
    CHEESE(6),
}

enum class RecipeDbTag(val id: Int) {
    MEAT(0),
    FISH(1),
    SEAFOOD(2),
    VEGETARIAN(3),
    VEGETABLES(4),
    FRUITS(5),
    BAKERY(6),
}

@Entity(
    tableName = "recipes",
    indices = [
        Index(
            value = ["creation_date"],
            unique = true,
        )
    ],
)
data class RecipeEntity(
    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = "id") val id: Int = 0,
    @ColumnInfo(name = "name") val name: String,
    @ColumnInfo(name = "recipe") val recipe: String,
    @ColumnInfo(name = "creation_date") val creationDate: Long,
    @ColumnInfo(name = "cooking_time") val cookingTime: Long,
    @ColumnInfo(name = "category") val category: RecipeDbCategory,
    @ColumnInfo(name = "tags") val tags: List<RecipeDbTag>,
    @ColumnInfo(name = "ingredients") val ingredients: Map<Int, Int>,
)