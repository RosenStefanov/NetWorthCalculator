package com.rosenstefanov.networthcalculator.feature.liabilities.impl.ui.editliability.models

sealed interface EditLiabilityUiState {

    data object Content : EditLiabilityUiState
}
