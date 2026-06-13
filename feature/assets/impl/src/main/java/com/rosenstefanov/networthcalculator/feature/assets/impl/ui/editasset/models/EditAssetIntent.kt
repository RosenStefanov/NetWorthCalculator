package com.rosenstefanov.networthcalculator.feature.assets.impl.ui.editasset.models

sealed interface EditAssetIntent {
    data class NameChanged(val name: String) : EditAssetIntent
    data class AmountChanged(val amount: String) : EditAssetIntent
    data class DescriptionChanged(val description: String) : EditAssetIntent
    data object SaveClicked : EditAssetIntent
    data object CloseClicked : EditAssetIntent
}
