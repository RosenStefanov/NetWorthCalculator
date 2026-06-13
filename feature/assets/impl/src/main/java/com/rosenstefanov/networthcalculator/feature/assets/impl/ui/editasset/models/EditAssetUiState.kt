package com.rosenstefanov.networthcalculator.feature.assets.impl.ui.editasset.models

sealed interface EditAssetUiState {

    data object Content : EditAssetUiState
}
