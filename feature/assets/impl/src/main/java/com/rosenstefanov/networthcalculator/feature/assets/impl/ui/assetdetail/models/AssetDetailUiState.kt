package com.rosenstefanov.networthcalculator.feature.assets.impl.ui.assetdetail.models

sealed interface AssetDetailUiState {

    data object Content : AssetDetailUiState
}
