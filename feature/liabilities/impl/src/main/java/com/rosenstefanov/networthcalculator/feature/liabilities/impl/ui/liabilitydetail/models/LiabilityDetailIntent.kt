package com.rosenstefanov.networthcalculator.feature.liabilities.impl.ui.liabilitydetail.models

sealed interface LiabilityDetailIntent {
    data object CloseClicked : LiabilityDetailIntent
}
