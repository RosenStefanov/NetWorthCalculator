package com.rosenstefanov.networthcalculator.feature.assets.impl.ui.assetdetail.models

sealed interface AssetDetailIntent {
    data object CloseClicked : AssetDetailIntent
    data class RangeSelected(val range: String) : AssetDetailIntent
    data object EditClicked : AssetDetailIntent
    data object DeleteClicked : AssetDetailIntent
    data object DeleteConfirmed : AssetDetailIntent
    data object DeleteDismissed : AssetDetailIntent
}
