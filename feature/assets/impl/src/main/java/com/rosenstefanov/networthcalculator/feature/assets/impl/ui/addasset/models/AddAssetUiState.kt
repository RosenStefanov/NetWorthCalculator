package com.rosenstefanov.networthcalculator.feature.assets.impl.ui.addasset.models

sealed interface AddAssetUiState {

    data object Content : AddAssetUiState
}
