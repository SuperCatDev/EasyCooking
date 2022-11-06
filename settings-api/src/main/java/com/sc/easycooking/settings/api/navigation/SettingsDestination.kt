package com.sc.easycooking.settings.api.navigation

import com.sc.easycooking.navigation.EcNavigationDestination

object SettingsDestination : EcNavigationDestination {
    override val route: String = "settings_route"
    override val destination: String = "settings_destination"
}