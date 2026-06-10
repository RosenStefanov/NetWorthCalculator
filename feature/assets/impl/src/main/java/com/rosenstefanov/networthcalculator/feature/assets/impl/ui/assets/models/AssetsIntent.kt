package com.rosenstefanov.networthcalculator.feature.assets.impl.ui.assets.models

sealed interface AssetsIntent {
    data object SettingsClicked : AssetsIntent
    data object AddAccountClicked : AssetsIntent
}
