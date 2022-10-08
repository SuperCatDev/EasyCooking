package com.sc.easycooking.db.database.util

import androidx.room.TypeConverter
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import com.sc.easycooking.db.database.model.QuantityDbType
import com.sc.easycooking.db.database.model.RecipeDbCategory
import com.sc.easycooking.db.database.model.RecipeDbTag

class RecipeCategoryConverter {
    @TypeConverter
    fun intToCategory(value: Int): RecipeDbCategory {
        return RecipeDbCategory.values().find { it.id == value }!!
    }

    @TypeConverter
    fun categoryToInt(category: RecipeDbCategory): Int = category.id
}

class RecipeTagsListConverter {
    @TypeConverter
    fun jsonToTagsList(json: String): List<RecipeDbTag> {
        return Gson().fromJson<List<Int>?>(json, object : TypeToken<List<Int>>() {}.type)
            .map { id ->
                RecipeDbTag.values().find { it.id == id }!!
            }
    }

    @TypeConverter
    fun tagsListToJson(list: List<RecipeDbTag>): String {
        return Gson().toJson(list.map { it.id })
    }
}

class IngredientsConverter {
    @TypeConverter
    fun jsonToMapOfIngredients(json: String): Map<Int, Int> {
        return Gson().fromJson(json, object : TypeToken<Map<Int, Int>>() {}.type)
    }

    @TypeConverter
    fun mapOfIngredientsToJson(map: Map<Int, Int>): String {
        return Gson().toJson(map)
    }
}

class QuantityConverter {
    @TypeConverter
    fun intToQuantity(value: Int): QuantityDbType {
        return QuantityDbType.values().find { it.id == value }!!
    }

    @TypeConverter
    fun quantityToInt(quantity: QuantityDbType): Int = quantity.id
}