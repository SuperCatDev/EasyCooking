package com.sc.easycooking.settings.impl.presentation

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.sc.easycooking.settings.api.domain.SettingsInteractor
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.stateIn
import javax.inject.Inject

@HiltViewModel
internal class SettingsListViewModel @Inject constructor(
    private val interactor: SettingsInteractor,
) : ViewModel() {
    fun observeMaterialYou(): StateFlow<Boolean> {
        return interactor.getDisplaySettingsFlow().map { it.materialYouEnabled }
            .stateIn(viewModelScope, started = SharingStarted.Eagerly, true)
    }

    fun changeMaterialYou(enabled: Boolean) {
        interactor.updateDisplaySetting(
            interactor.getDisplaySettingsFlow().value.copy(
                materialYouEnabled = enabled
            )
        )
    }
}