package com.rosenstefanov.networthcalculator.feature.assets.impl.ui.assets.models

sealed interface AssetsEffect {
    data object NavigateToSettings : AssetsEffect
    data object NavigateToAddAccount : AssetsEffect
    data object NavigateToDetail : AssetsEffect
}
