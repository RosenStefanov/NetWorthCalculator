package com.rosenstefanov.networthcalculator.feature.liabilities.impl.ui.addliability.models

sealed interface AddLiabilityIntent {
    data object SaveClicked : AddLiabilityIntent
    data object CloseClicked : AddLiabilityIntent
}
