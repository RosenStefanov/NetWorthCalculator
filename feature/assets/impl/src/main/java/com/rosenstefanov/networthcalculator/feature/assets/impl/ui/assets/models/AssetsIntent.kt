package com.rosenstefanov.networthcalculator.feature.assets.impl.ui.assets.models

import com.rosenstefanov.networthcalculator.core.ui.models.SortMode

sealed interface AssetsIntent {
    data object SettingsClicked : AssetsIntent
    data object AddAccountClicked : AssetsIntent
    data object HoldingClicked : AssetsIntent
    data class SortSelected(val mode: SortMode) : AssetsIntent
}
