package com.rosenstefanov.networthcalculator.feature.database.api.model

import java.time.Instant

data class TrendPoint(
    val type: AccountType,
    val value: Money,
    val recordedAt: Instant,
)
