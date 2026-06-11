package com.rosenstefanov.networthcalculator.feature.assets.impl.ui.addasset.models

sealed interface AddAssetEffect {
    data object NavigateBack : AddAssetEffect
}
