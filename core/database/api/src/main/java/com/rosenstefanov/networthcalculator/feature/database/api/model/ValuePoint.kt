package com.rosenstefanov.networthcalculator.feature.database.api.model

import java.time.Instant

data class ValuePoint(
    val value: Money,
    val recordedAt: Instant,
)
