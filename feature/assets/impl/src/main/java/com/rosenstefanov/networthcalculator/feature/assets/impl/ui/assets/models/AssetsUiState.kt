package com.rosenstefanov.networthcalculator.feature.assets.impl.ui.assets.models

sealed interface AssetsUiState {

    data object Loading : AssetsUiState

    data object Content : AssetsUiState
}
