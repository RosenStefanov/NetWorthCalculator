package com.rosenstefanov.networthcalculator.feature.dashboard.impl.ui.dashboard.models

import com.rosenstefanov.networthcalculator.core.ui.models.HoldingType

sealed interface DashboardIntent {
    data object Refresh : DashboardIntent
    data object RetryClicked : DashboardIntent
    data object SettingsClicked : DashboardIntent
    data class RangeSelected(val range: String) : DashboardIntent
    data class HoldingTypeSelected(val type: HoldingType) : DashboardIntent
    data class HoldingClicked(val type: HoldingType) : DashboardIntent
}
