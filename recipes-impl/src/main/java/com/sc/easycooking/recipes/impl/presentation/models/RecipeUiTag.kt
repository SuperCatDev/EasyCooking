package com.sc.easycooking.recipes.impl.presentation.models

import android.os.Parcelable
import com.sc.easycooking.recipes.api.models.RecipeTag
import kotlinx.parcelize.Parcelize

@Parcelize
data class RecipeUiTag(
    val tag: RecipeTag,
    val name: String,
): Parcelable