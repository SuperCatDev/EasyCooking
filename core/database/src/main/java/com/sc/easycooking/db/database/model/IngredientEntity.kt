package com.sc.easycooking.db.database.model

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

enum class QuantityDbType(val id: Int) {
    ML(0),
    LITRES(1),
    GRAMS(2),
    KG(3),
    AMOUNT(4),
}

@Entity(
    tableName = "ingredients",
)
data class IngredientEntity(
    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = "id") val id: Int = 0,
    @ColumnInfo(name = "name") val name: String,
    @ColumnInfo(name = "quantity") val quantity: QuantityDbType,
)