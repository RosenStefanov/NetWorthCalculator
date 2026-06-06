package com.rosenstefanov.networthcalculator.feature.dashboard.impl.ui.dashboard.models

/**
 * A single row in the "Top holdings" list. Assets and liabilities are merged;
 * [isLiability] drives the sign and color of the displayed amount.
 */
data class HoldingRow(
    val id: Long,
    val label: String,
    val emoji: String,
    val amount: Money,
    val isLiability: Boolean,
)
