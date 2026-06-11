package com.rosenstefanov.networthcalculator.feature.assets.impl.navigation

import androidx.navigation3.runtime.EntryProviderScope
import androidx.navigation3.runtime.NavKey
import com.rosenstefanov.networthcalculator.core.navigation.Navigator
import com.rosenstefanov.networthcalculator.feature.assets.api.AddAssetRoute
import com.rosenstefanov.networthcalculator.feature.assets.api.AssetsRoute
import com.rosenstefanov.networthcalculator.feature.assets.impl.ui.addasset.AddAssetScreen
import com.rosenstefanov.networthcalculator.feature.assets.impl.ui.assets.AssetsScreen
import com.rosenstefanov.networthcalculator.feature.settings.api.SettingsRoute

fun EntryProviderScope<NavKey>.assetsEntries(navigator: Navigator) {
    entry<AssetsRoute> {
        AssetsScreen(
            onNavigateToSettings = { navigator.navigate(SettingsRoute) },
            onNavigateToAddAccount = { navigator.navigate(AddAssetRoute) },
        )
    }
    entry<AddAssetRoute> {
        AddAssetScreen(onNavigateBack = { navigator.goBack() })
    }
}
