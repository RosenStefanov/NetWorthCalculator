package com.rosenstefanov.networthcalculator.feature.liabilities.impl.ui.addliability.models

import com.rosenstefanov.networthcalculator.core.ui.models.Category

sealed interface AddLiabilityIntent {
    data class NameChanged(val name: String) : AddLiabilityIntent
    data class AmountChanged(val amount: String) : AddLiabilityIntent
    data class CategorySelected(val category: Category) : AddLiabilityIntent
    data class DescriptionChanged(val description: String) : AddLiabilityIntent
    data object SaveClicked : AddLiabilityIntent
    data object CloseClicked : AddLiabilityIntent
}
