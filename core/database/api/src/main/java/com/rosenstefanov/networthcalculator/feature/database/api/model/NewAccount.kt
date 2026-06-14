package com.rosenstefanov.networthcalculator.feature.database.api.model

data class NewAccount(
    val type: AccountType,
    val name: String,
    val category: String,
    val value: Money,
    val note: String? = null,
)
