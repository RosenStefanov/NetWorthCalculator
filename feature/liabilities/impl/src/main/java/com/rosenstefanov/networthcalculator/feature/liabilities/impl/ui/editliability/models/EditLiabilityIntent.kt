package com.rosenstefanov.networthcalculator.feature.liabilities.impl.ui.editliability.models

sealed interface EditLiabilityIntent {
    data object CloseClicked : EditLiabilityIntent
}
