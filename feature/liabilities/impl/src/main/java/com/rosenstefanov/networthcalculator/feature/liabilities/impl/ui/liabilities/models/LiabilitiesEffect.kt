package com.rosenstefanov.networthcalculator.feature.liabilities.impl.ui.liabilities.models

sealed interface LiabilitiesEffect {
    data object NavigateToSettings : LiabilitiesEffect
    data object NavigateToAddAccount : LiabilitiesEffect
}
