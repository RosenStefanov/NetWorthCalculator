package com.rosenstefanov.networthcalculator.feature.dashboard.impl.navigation

import androidx.navigation3.runtime.EntryProviderScope
import androidx.navigation3.runtime.NavKey
import com.rosenstefanov.networthcalculator.core.navigation.Navigator
import com.rosenstefanov.networthcalculator.feature.dashboard.api.DashboardRoute
import com.rosenstefanov.networthcalculator.feature.dashboard.impl.ui.dashboard.DashboardScreen
import com.rosenstefanov.networthcalculator.feature.settings.api.SettingsRoute

fun EntryProviderScope<NavKey>.dashboardEntries(navigator: Navigator) {
    entry<DashboardRoute> {
        DashboardScreen(
            onNavigateToSettings = { navigator.navigate(SettingsRoute) },
        )
    }
}
