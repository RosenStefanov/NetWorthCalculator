package com.rosenstefanov.networthcalculator.feature.assets.impl.ui.editasset.models

sealed interface EditAssetUiState {

    data class Content(
        val name: String = "",
        val amount: String = "",
        val description: String = "",
    ) : EditAssetUiState
}
