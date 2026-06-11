package com.rosenstefanov.networthcalculator.core.ui.models

import androidx.annotation.DrawableRes
import androidx.compose.ui.graphics.Color

data class Holding(
    val name: String,
    val category: String,
    val amount: Long,
    val color: Color,
    @DrawableRes val icon: Int,
)
