package com.rosenstefanov.networthcalculator.feature.assets.impl.ui.assetdetail.models

sealed interface AssetDetailIntent {
    data object CloseClicked : AssetDetailIntent
}
