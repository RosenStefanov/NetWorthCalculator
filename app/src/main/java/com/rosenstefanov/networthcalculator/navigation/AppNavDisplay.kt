package com.rosenstefanov.networthcalculator.navigation

import androidx.compose.runtime.Composable
import androidx.navigation3.runtime.entryProvider
import androidx.navigation3.ui.NavDisplay
import com.rosenstefanov.networthcalculator.core.navigation.Navigator
import com.rosenstefanov.networthcalculator.feature.assets.impl.navigation.assetsEntries
import com.rosenstefanov.networthcalculator.feature.dashboard.impl.navigation.dashboardEntries
import com.rosenstefanov.networthcalculator.feature.liabilities.impl.navigation.liabilitiesEntries
import com.rosenstefanov.networthcalculator.feature.settings.impl.navigation.settingsEntries

@Composable
fun AppNavDisplay(navigator: Navigator) {
    NavDisplay(
        backStack = navigator.currentBackStack,
        onBack = { navigator.goBack() },
        entryProvider = entryProvider {
            dashboardEntries(navigator)
            assetsEntries(navigator)
            liabilitiesEntries(navigator)
            settingsEntries(navigator)
        },
    )
}
