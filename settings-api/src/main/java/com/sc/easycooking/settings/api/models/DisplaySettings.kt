package com.sc.easycooking.settings.api.models

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class DisplaySettings(
    val themeSetting: ThemeSetting,
    val materialYouEnabled: Boolean = true,
) : Parcelable

enum class ThemeSetting(val id: String) {
    DEFAULT("DEFAULT"),
    LIGHT("LIGHT"),
    DARK("DARK"),
}