package com.rosenstefanov.networthcalculator.feature.settings.impl.ui.settings.models

data class SettingsUiState(
    val name: String = "",
    val currency: String = "USD",
    val theme: ThemeMode = ThemeMode.System,
)
