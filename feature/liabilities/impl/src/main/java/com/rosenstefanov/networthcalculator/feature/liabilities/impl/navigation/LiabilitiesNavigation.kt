package com.rosenstefanov.networthcalculator.feature.liabilities.impl.navigation

import androidx.navigation3.runtime.EntryProviderScope
import androidx.navigation3.runtime.NavKey
import com.rosenstefanov.networthcalculator.core.navigation.Navigator
import com.rosenstefanov.networthcalculator.feature.liabilities.api.AddLiabilityRoute
import com.rosenstefanov.networthcalculator.feature.liabilities.api.EditLiabilityRoute
import com.rosenstefanov.networthcalculator.feature.liabilities.api.LiabilitiesRoute
import com.rosenstefanov.networthcalculator.feature.liabilities.api.LiabilityDetailRoute
import com.rosenstefanov.networthcalculator.feature.liabilities.impl.ui.addliability.AddLiabilityScreen
import com.rosenstefanov.networthcalculator.feature.liabilities.impl.ui.editliability.EditLiabilityScreen
import com.rosenstefanov.networthcalculator.feature.liabilities.impl.ui.liabilities.LiabilitiesScreen
import com.rosenstefanov.networthcalculator.feature.liabilities.impl.ui.liabilitydetail.LiabilityDetailScreen
import com.rosenstefanov.networthcalculator.feature.settings.api.SettingsRoute

fun EntryProviderScope<NavKey>.liabilitiesEntries(navigator: Navigator) {
    entry<LiabilitiesRoute> {
        LiabilitiesScreen(
            onNavigateToSettings = { navigator.navigate(SettingsRoute) },
            onNavigateToAddAccount = { navigator.navigate(AddLiabilityRoute) },
            onNavigateToHoldingDetail = { navigator.navigate(LiabilityDetailRoute) },
        )
    }
    entry<AddLiabilityRoute> {
        AddLiabilityScreen(onNavigateBack = { navigator.goBack() })
    }
    entry<LiabilityDetailRoute> {
        LiabilityDetailScreen(
            onNavigateBack = { navigator.goBack() },
            onNavigateToEdit = { navigator.navigate(EditLiabilityRoute) },
        )
    }
    entry<EditLiabilityRoute> {
        EditLiabilityScreen(onNavigateBack = { navigator.goBack() })
    }
}
