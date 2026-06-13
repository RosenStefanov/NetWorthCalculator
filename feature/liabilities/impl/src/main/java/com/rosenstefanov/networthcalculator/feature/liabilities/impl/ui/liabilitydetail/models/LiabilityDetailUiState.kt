package com.rosenstefanov.networthcalculator.feature.liabilities.impl.ui.liabilitydetail.models

sealed interface LiabilityDetailUiState {

    data class Content(
        val range: String = "1Y",
        val showDeleteDialog: Boolean = false,
    ) : LiabilityDetailUiState
}
