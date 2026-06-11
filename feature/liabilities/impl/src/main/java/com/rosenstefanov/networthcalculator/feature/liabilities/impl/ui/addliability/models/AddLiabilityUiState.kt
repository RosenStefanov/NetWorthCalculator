package com.rosenstefanov.networthcalculator.feature.liabilities.impl.ui.addliability.models

sealed interface AddLiabilityUiState {

    data object Content : AddLiabilityUiState
}
