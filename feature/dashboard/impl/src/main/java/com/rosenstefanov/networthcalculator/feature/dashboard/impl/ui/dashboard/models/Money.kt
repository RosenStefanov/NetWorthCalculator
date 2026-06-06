package com.rosenstefanov.networthcalculator.feature.dashboard.impl.ui.dashboard.models

import java.math.BigDecimal
import java.text.NumberFormat
import java.util.Currency
import java.util.Locale

/**
 * Temporary money type local to the dashboard feature for Chunk 1 (UI-first).
 * Will be promoted to a shared domain model in Chunk 6 (Lock Domain Model).
 *
 * Formatting uses a fixed Locale so snapshot tests are deterministic across machines.
 */
data class Money(
    val amount: BigDecimal,
    val currencyCode: String,
) {
    fun formatted(): String {
        val format = NumberFormat.getCurrencyInstance(Locale.US)
        format.currency = Currency.getInstance(currencyCode)
        return format.format(amount)
    }
}
