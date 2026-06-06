package com.rosenstefanov.networthcalculator.feature.dashboard.impl.ui.dashboard.models

sealed interface DashboardIntent {
    data object Refresh : DashboardIntent
    data object RetryClicked : DashboardIntent
    data object SettingsClicked : DashboardIntent
}
