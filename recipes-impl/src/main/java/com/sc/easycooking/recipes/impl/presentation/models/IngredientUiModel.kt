package com.sc.easycooking.recipes.impl.presentation.models

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
internal data class IngredientUiModel(
    val name: String,
    val quantity: String,
    val amount: Int,
): Parcelable