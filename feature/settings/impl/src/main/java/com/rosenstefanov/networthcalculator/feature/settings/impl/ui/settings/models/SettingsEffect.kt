package com.rosenstefanov.networthcalculator.feature.settings.impl.ui.settings.models

sealed interface SettingsEffect {
    data object NavigateBack : SettingsEffect
}
