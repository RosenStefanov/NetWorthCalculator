package com.rosenstefanov.networthcalculator.feature.liabilities.impl.ui.liabilitydetail.models

sealed interface LiabilityDetailEffect {
    data object NavigateBack : LiabilityDetailEffect
}
