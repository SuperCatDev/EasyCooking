package com.sc.easycooking.db.database.model

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.Index
import androidx.room.PrimaryKey

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
    @ColumnInfo(name = "category_id") val categoryId: Int,
    @ColumnInfo(name = "tag_ids") val tagIds: List<Int>,
    @ColumnInfo(name = "ingredients") val ingredients: List<IngredientDbModel>,
)