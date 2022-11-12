package com.sc.easycooking.settings.impl.repository

import android.content.Context
import com.sc.easycooking.settings.api.models.DisplaySettings
import com.sc.easycooking.settings.api.models.ThemeSetting
import dagger.hilt.android.qualifiers.ApplicationContext
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import javax.inject.Inject

internal class SettingsRepository @Inject constructor(
    @ApplicationContext private val context: Context
) {
    private val prefs = context.getSharedPreferences(PREF_NAME, Context.MODE_PRIVATE)
    private val displaySettingsFlow: MutableStateFlow<DisplaySettings>

    init {
        val savedTheme = prefs.getInt(THEME_SETTINGS_NAME, THEME_SETTINGS_DEFAULT).mapToThemeValue()
        val savedMaterialYou = prefs.getBoolean(MATERIAL_YOU_SETTINGS_NAME, true)

        displaySettingsFlow = MutableStateFlow(
            DisplaySettings(themeSetting = savedTheme, materialYouEnabled = savedMaterialYou)
        )
    }

    fun getDisplaySettingsFlow(): StateFlow<DisplaySettings> {
        return displaySettingsFlow
    }

    fun updateDisplaySetting(displaySettings: DisplaySettings) {
        val current = displaySettingsFlow.value

        if (current == displaySettings) return

        displaySettingsFlow.value = displaySettings

        updateStorageDisplaySettings(displaySettings)
    }

    private fun updateStorageDisplaySettings(displaySettings: DisplaySettings) {
        prefs.edit()
            .putInt(THEME_SETTINGS_NAME, displaySettings.themeSetting.mapToThemeInt())
            .putBoolean(MATERIAL_YOU_SETTINGS_NAME, displaySettings.materialYouEnabled)
            .apply()
    }

    private fun Int.mapToThemeValue() = when (this) {
        THEME_SETTINGS_DEFAULT -> ThemeSetting.DEFAULT
        THEME_SETTINGS_DARK -> ThemeSetting.DARK
        THEME_SETTINGS_LIGHT -> ThemeSetting.LIGHT
        else -> ThemeSetting.DEFAULT
    }

    private fun ThemeSetting.mapToThemeInt() = when (this) {
        ThemeSetting.DEFAULT -> THEME_SETTINGS_DEFAULT
        ThemeSetting.LIGHT -> THEME_SETTINGS_LIGHT
        ThemeSetting.DARK -> THEME_SETTINGS_DARK
    }

    private companion object {
        const val PREF_NAME = "settings_repo"
        const val MATERIAL_YOU_SETTINGS_NAME = "material_you_settings"
        const val THEME_SETTINGS_NAME = "theme_settings"
        const val THEME_SETTINGS_DEFAULT = 0
        const val THEME_SETTINGS_DARK = 1
        const val THEME_SETTINGS_LIGHT = 2
    }
}