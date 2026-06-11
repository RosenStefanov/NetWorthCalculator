package com.rosenstefanov.networthcalculator.feature.liabilities.impl.ui.liabilities.models

import com.rosenstefanov.networthcalculator.core.ui.models.AllocItem
import com.rosenstefanov.networthcalculator.core.ui.models.Holding
import com.rosenstefanov.networthcalculator.core.ui.models.SortMode

sealed interface LiabilitiesUiState {

    data object Loading : LiabilitiesUiState

    data class Content(
        val total: String,
        val deltaText: String,
        val isGain: Boolean,
        val summary: String,
        val allocation: List<AllocItem>,
        val allocationTotal: Long,
        val holdings: List<Holding>,
        val holdingsTotal: Long,
        val selectedSort: SortMode,
    ) : LiabilitiesUiState
}
