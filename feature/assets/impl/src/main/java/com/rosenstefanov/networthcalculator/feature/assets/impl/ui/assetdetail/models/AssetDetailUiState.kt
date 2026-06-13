package com.rosenstefanov.networthcalculator.feature.assets.impl.ui.assetdetail.models

sealed interface AssetDetailUiState {

    data class Content(
        val range: String = "1Y",
        val showDeleteDialog: Boolean = false,
    ) : AssetDetailUiState
}
