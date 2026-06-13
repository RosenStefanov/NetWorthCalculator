package com.rosenstefanov.networthcalculator.feature.liabilities.impl.ui.editliability.models

sealed interface EditLiabilityUiState {

    data class Content(
        val name: String = "",
        val amount: String = "",
        val description: String = "",
    ) : EditLiabilityUiState
}
