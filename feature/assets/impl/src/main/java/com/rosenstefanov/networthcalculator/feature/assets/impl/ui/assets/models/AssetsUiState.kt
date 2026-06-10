package com.rosenstefanov.networthcalculator.feature.assets.impl.ui.assets.models

sealed interface AssetsUiState {

    data object Loading : AssetsUiState

    data class Content(
        val total: String,
        val deltaText: String,
        val isGain: Boolean,
        val summary: String,
    ) : AssetsUiState
}
