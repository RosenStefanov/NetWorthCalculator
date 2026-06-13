package com.rosenstefanov.networthcalculator.feature.assets.impl.ui.editasset.models

sealed interface EditAssetEffect {
    data object NavigateBack : EditAssetEffect
}
