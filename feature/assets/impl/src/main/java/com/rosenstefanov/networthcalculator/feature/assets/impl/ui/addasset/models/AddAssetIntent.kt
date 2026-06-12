package com.rosenstefanov.networthcalculator.feature.assets.impl.ui.addasset.models

import com.rosenstefanov.networthcalculator.core.ui.models.Category

sealed interface AddAssetIntent {
    data class NameChanged(val name: String) : AddAssetIntent
    data class AmountChanged(val amount: String) : AddAssetIntent
    data class CategorySelected(val category: Category) : AddAssetIntent
    data class DescriptionChanged(val description: String) : AddAssetIntent
    data object SaveClicked : AddAssetIntent
    data object CloseClicked : AddAssetIntent
}
