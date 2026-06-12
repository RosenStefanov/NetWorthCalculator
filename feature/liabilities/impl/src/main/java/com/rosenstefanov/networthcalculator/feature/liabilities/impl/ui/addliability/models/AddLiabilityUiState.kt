package com.rosenstefanov.networthcalculator.feature.liabilities.impl.ui.addliability.models

import com.rosenstefanov.networthcalculator.core.ui.models.Category
import com.rosenstefanov.networthcalculator.core.ui.models.LiabilityCategories

sealed interface AddLiabilityUiState {

    data class Content(
        val name: String = "",
        val amount: String = "",
        val category: Category = LiabilityCategories.first(),
        val description: String = "",
    ) : AddLiabilityUiState
}
