package com.rosenstefanov.networthcalculator.feature.liabilities.impl.ui.editliability.models

sealed interface EditLiabilityIntent {
    data class NameChanged(val name: String) : EditLiabilityIntent
    data class AmountChanged(val amount: String) : EditLiabilityIntent
    data class DescriptionChanged(val description: String) : EditLiabilityIntent
    data object SaveClicked : EditLiabilityIntent
    data object CloseClicked : EditLiabilityIntent
}
