package com.rosenstefanov.networthcalculator.feature.liabilities.impl.navigation

import androidx.navigation3.runtime.EntryProviderScope
import androidx.navigation3.runtime.NavKey
import com.rosenstefanov.networthcalculator.core.navigation.Navigator
import com.rosenstefanov.networthcalculator.feature.liabilities.api.LiabilitiesRoute
import com.rosenstefanov.networthcalculator.feature.liabilities.impl.ui.liabilities.LiabilitiesScreen
import com.rosenstefanov.networthcalculator.feature.settings.api.SettingsRoute

fun EntryProviderScope<NavKey>.liabilitiesEntries(navigator: Navigator) {
    entry<LiabilitiesRoute> {
        LiabilitiesScreen(
            onNavigateToSettings = { navigator.navigate(SettingsRoute) },
            // TODO: navigate to the Add Account flow (pre-scoped to liabilities) once it exists.
            onNavigateToAddAccount = {},
        )
    }
}
