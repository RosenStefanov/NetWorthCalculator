package com.rosenstefanov.networthcalculator.feature.assets.impl.ui.editasset.models

sealed interface EditAssetIntent {
    data object CloseClicked : EditAssetIntent
}
