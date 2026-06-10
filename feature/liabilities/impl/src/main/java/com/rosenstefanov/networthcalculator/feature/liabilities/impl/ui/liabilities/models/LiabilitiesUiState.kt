package com.rosenstefanov.networthcalculator.feature.liabilities.impl.ui.liabilities.models

sealed interface LiabilitiesUiState {

    data object Loading : LiabilitiesUiState

    data object Content : LiabilitiesUiState
}
