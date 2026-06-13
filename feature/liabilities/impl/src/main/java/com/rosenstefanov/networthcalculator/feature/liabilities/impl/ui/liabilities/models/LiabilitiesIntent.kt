package com.rosenstefanov.networthcalculator.feature.liabilities.impl.ui.liabilities.models

import com.rosenstefanov.networthcalculator.core.ui.models.SortMode

sealed interface LiabilitiesIntent {
    data object SettingsClicked : LiabilitiesIntent
    data object AddAccountClicked : LiabilitiesIntent
    data object HoldingClicked : LiabilitiesIntent
    data class SortSelected(val mode: SortMode) : LiabilitiesIntent
}
