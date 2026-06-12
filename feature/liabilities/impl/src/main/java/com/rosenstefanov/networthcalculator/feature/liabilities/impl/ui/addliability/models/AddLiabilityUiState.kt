package com.rosenstefanov.networthcalculator.feature.liabilities.impl.ui.addliability.models

sealed interface AddLiabilityUiState {

    // Placeholder form state — only the name field exists so far; more inputs come in a later chunk.
    data class Content(val name: String = "") : AddLiabilityUiState
}
