package com.rosenstefanov.networthcalculator.feature.liabilities.impl.ui.liabilities.models

sealed interface LiabilitiesIntent {
    data object SettingsClicked : LiabilitiesIntent
    data object AddAccountClicked : LiabilitiesIntent
}
