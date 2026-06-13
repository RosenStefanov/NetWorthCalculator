package com.rosenstefanov.networthcalculator.feature.assets.impl.ui.assetdetail.models

sealed interface AssetDetailEffect {
    data object NavigateBack : AssetDetailEffect
    data object NavigateToEdit : AssetDetailEffect
}
