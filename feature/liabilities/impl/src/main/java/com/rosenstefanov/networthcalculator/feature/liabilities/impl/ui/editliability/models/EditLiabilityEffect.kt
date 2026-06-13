package com.rosenstefanov.networthcalculator.feature.liabilities.impl.ui.editliability.models

sealed interface EditLiabilityEffect {
    data object NavigateBack : EditLiabilityEffect
}
