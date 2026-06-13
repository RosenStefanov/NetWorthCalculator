package com.rosenstefanov.networthcalculator.feature.liabilities.impl.ui.liabilitydetail.models

sealed interface LiabilityDetailUiState {

    data object Content : LiabilityDetailUiState
}
