package com.rosenstefanov.networthcalculator.feature.liabilities.impl.ui.addliability.models

sealed interface AddLiabilityEffect {
    data object NavigateBack : AddLiabilityEffect
}
