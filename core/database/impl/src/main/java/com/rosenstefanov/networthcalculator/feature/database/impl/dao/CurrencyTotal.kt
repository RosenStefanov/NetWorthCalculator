package com.rosenstefanov.networthcalculator.feature.database.impl.dao

import com.rosenstefanov.networthcalculator.feature.database.impl.entity.AccountType

data class CurrencyTotal(
    val type: AccountType,
    val currencyCode: String,
    val totalMinor: Long,
)
