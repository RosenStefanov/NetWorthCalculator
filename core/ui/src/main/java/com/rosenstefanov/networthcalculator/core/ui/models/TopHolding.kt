package com.rosenstefanov.networthcalculator.core.ui.models

import androidx.annotation.DrawableRes

data class TopHolding(
    @DrawableRes val iconRes: Int,
    val name: String,
    val category: String,
    val amountLabel: String,
    val amount: Long,
)
