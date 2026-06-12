package com.rosenstefanov.networthcalculator.feature.liabilities.impl.ui.addliability.models

sealed interface AddLiabilityIntent {
    data class NameChanged(val name: String) : AddLiabilityIntent
    data object SaveClicked : AddLiabilityIntent
    data object CloseClicked : AddLiabilityIntent
}
