package com.rosenstefanov.networthcalculator.feature.assets.impl.ui.addasset.models

sealed interface AddAssetIntent {
    data class NameChanged(val name: String) : AddAssetIntent
    data object SaveClicked : AddAssetIntent
    data object CloseClicked : AddAssetIntent
}
