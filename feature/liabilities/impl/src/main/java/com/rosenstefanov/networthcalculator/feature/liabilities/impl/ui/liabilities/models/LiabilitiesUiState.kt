package com.rosenstefanov.networthcalculator.feature.liabilities.impl.ui.liabilities.models

sealed interface LiabilitiesUiState {

    data object Loading : LiabilitiesUiState

    data class Content(
        val total: String,
        val deltaText: String,
        val isGain: Boolean,
        val summary: String,
    ) : LiabilitiesUiState
}
