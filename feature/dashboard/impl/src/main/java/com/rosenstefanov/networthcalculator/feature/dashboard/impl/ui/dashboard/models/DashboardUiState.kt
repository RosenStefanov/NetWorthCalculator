package com.rosenstefanov.networthcalculator.feature.dashboard.impl.ui.dashboard.models

sealed interface DashboardUiState {

    data object Loading : DashboardUiState

    data object Empty : DashboardUiState

    data class Content(
        val netWorth: Money,
        val assetsTotal: Money,
        val liabilitiesTotal: Money,
        val topHoldings: List<HoldingRow>,
    ) : DashboardUiState

    data class Error(val message: String) : DashboardUiState
}
