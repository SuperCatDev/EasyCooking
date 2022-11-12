package com.sc.easycooking.settings.impl.domain

import com.sc.easycooking.settings.api.domain.SettingsInteractor
import com.sc.easycooking.settings.api.models.DisplaySettings
import com.sc.easycooking.settings.impl.repository.SettingsRepository
import kotlinx.coroutines.flow.StateFlow
import javax.inject.Inject

internal class SettingsInteractorImpl @Inject constructor(
    private val repository: SettingsRepository,
) : SettingsInteractor {

    override fun getDisplaySettingsFlow(): StateFlow<DisplaySettings> {
        return repository.getDisplaySettingsFlow()
    }

    override fun updateDisplaySetting(displaySettings: DisplaySettings) {
        repository.updateDisplaySetting(displaySettings)
    }
}