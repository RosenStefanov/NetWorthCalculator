package com.rosenstefanov.networthcalculator.feature.settings.impl.ui.settings.models

sealed interface SettingsIntent {
    data class NameChanged(val name: String) : SettingsIntent
    data class ThemeSelected(val theme: ThemeMode) : SettingsIntent
    data object CurrencyClicked : SettingsIntent
    data object RateClicked : SettingsIntent
    data object PrivacyClicked : SettingsIntent
    data object BackClicked : SettingsIntent
}
