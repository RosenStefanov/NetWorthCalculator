package com.rosenstefanov.networthcalculator.feature.database.api.model

data class CurrencyTotal(
    val type: AccountType,
    val total: Money,
)
