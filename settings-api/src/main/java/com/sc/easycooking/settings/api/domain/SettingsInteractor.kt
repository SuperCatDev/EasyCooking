package com.sc.easycooking.settings.api.domain

import com.sc.easycooking.settings.api.models.DisplaySettings
import kotlinx.coroutines.flow.StateFlow

interface SettingsInteractor {
    fun getDisplaySettingsFlow(): StateFlow<DisplaySettings>
    fun updateDisplaySetting(displaySettings: DisplaySettings)
}