package com.rosenstefanov.networthcalculator.feature.database.api.model

import java.time.Instant

data class Account(
    val id: Long,
    val type: AccountType,
    val name: String,
    val category: String,
    val value: Money,
    val note: String?,
    val createdAt: Instant,
    val updatedAt: Instant,
)
