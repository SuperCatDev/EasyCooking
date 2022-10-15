package com.sc.easycooking.db.database.util

import androidx.room.TypeConverter
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import com.sc.easycooking.db.database.model.IngredientDbModel

class RecipeTagsListConverter {
    @TypeConverter
    fun jsonToTagsList(json: String): List<Int> {
        return Gson().fromJson(json, object : TypeToken<List<Int>>() {}.type)
    }

    @TypeConverter
    fun tagsListToJson(list: List<Int>): String {
        return Gson().toJson(list)
    }
}

class IngredientsConverter {
    @TypeConverter
    fun jsonToMapOfIngredients(json: String): List<IngredientDbModel> {
        return Gson().fromJson(json, object : TypeToken<List<IngredientDbModel>>() {}.type)
    }

    @TypeConverter
    fun mapOfIngredientsToJson(list: List<IngredientDbModel>): String {
        return Gson().toJson(list)
    }
}