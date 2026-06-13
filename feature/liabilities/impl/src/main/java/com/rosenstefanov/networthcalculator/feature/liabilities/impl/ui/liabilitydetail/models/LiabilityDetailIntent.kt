package com.rosenstefanov.networthcalculator.feature.liabilities.impl.ui.liabilitydetail.models

sealed interface LiabilityDetailIntent {
    data object CloseClicked : LiabilityDetailIntent
    data class RangeSelected(val range: String) : LiabilityDetailIntent
    data object EditClicked : LiabilityDetailIntent
    data object DeleteClicked : LiabilityDetailIntent
    data object DeleteConfirmed : LiabilityDetailIntent
    data object DeleteDismissed : LiabilityDetailIntent
}
