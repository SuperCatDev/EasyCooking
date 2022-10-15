package com.sc.easycooking.db.database.model

import com.google.gson.annotations.SerializedName

data class IngredientDbModel(
    @SerializedName("name") val name: String,
    @SerializedName("quantity_type") val quantityType: Int,
    @SerializedName("amount") val amount: Int,
)