package com.rosenstefanov.networthcalculator.feature.database.impl.dao

import com.rosenstefanov.networthcalculator.feature.database.impl.entity.AccountType

data class TypedAccountValue(
    val type: AccountType,
    val amountMinor: Long,
    val recordedAt: Long,
    val currencyCode: String,
)
