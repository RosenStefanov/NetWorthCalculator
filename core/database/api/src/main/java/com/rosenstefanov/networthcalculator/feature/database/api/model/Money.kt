package com.rosenstefanov.networthcalculator.feature.database.api.model

import java.math.BigDecimal
import java.math.RoundingMode
import java.util.Currency

data class Money(
    val amount: BigDecimal,
    val currencyCode: String,
) {
    fun toMinorUnits(): Long =
        amount.movePointRight(fractionDigits(currencyCode))
            .setScale(0, RoundingMode.HALF_UP)
            .longValueExact()

    companion object {
        fun ofMinor(minorUnits: Long, currencyCode: String): Money =
            Money(
                amount = BigDecimal.valueOf(minorUnits).movePointLeft(fractionDigits(currencyCode)),
                currencyCode = currencyCode,
            )

        private fun fractionDigits(currencyCode: String): Int =
            Currency.getInstance(currencyCode).defaultFractionDigits.coerceAtLeast(0)
    }
}
