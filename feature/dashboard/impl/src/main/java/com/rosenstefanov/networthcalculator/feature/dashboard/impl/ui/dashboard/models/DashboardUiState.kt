package com.rosenstefanov.networthcalculator.feature.dashboard.impl.ui.dashboard.models

sealed interface DashboardUiState {

    data object Loading : DashboardUiState

    data object Empty : DashboardUiState

    data class Content(
        val netWorth: Money,
        val assetsTotal: Money,
        val liabilitiesTotal: Money,
        val assetsWeight: Float,
        val trend: NetWorthTrend,
        val ranges: List<String>,
        val selectedRange: String,
        val topHoldings: List<HoldingRow>,
        val change: NetWorthChange? = null,
    ) : DashboardUiState

    data class NetWorthChange(
        val label: String,
        val isGain: Boolean,
    )

    data class NetWorthTrend(
        val assets: List<Float>,
        val liabilities: List<Float>,
        val monthLabels: List<String>,
    )

    data class Error(val message: String) : DashboardUiState
}
