package com.rosenstefanov.networthcalculator.feature.dashboard.impl.ui.dashboard.models

sealed interface DashboardEffect {
    data object NavigateToSettings : DashboardEffect
}
