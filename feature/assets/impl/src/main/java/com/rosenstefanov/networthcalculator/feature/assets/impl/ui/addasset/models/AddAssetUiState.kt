package com.rosenstefanov.networthcalculator.feature.assets.impl.ui.addasset.models

import com.rosenstefanov.networthcalculator.core.ui.models.AssetCategories
import com.rosenstefanov.networthcalculator.core.ui.models.Category

sealed interface AddAssetUiState {

    data class Content(
        val name: String = "",
        val amount: String = "",
        val category: Category = AssetCategories.first(),
        val description: String = "",
    ) : AddAssetUiState
}
