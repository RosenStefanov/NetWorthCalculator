package com.rosenstefanov.networthcalculator.feature.settings.impl.navigation

import androidx.navigation3.runtime.EntryProviderScope
import androidx.navigation3.runtime.NavKey
import com.rosenstefanov.networthcalculator.core.navigation.Navigator
import com.rosenstefanov.networthcalculator.feature.settings.api.SettingsRoute
import com.rosenstefanov.networthcalculator.feature.settings.impl.SettingsScreen

fun EntryProviderScope<NavKey>.settingsEntries(navigator: Navigator) {
    entry<SettingsRoute> {
        SettingsScreen(onBack = { navigator.goBack() })
    }
}
