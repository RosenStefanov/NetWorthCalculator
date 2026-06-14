package com.rosenstefanov.networthcalculator.feature.database.impl.repository

import com.rosenstefanov.networthcalculator.feature.database.api.model.Account
import com.rosenstefanov.networthcalculator.feature.database.api.model.CurrencyTotal
import com.rosenstefanov.networthcalculator.feature.database.api.model.Money
import com.rosenstefanov.networthcalculator.feature.database.api.model.TrendPoint
import com.rosenstefanov.networthcalculator.feature.database.api.model.ValuePoint
import java.time.Instant
import com.rosenstefanov.networthcalculator.feature.database.impl.dao.CurrencyTotal as CurrencyTotalRow
import com.rosenstefanov.networthcalculator.feature.database.impl.dao.TypedAccountValue
import com.rosenstefanov.networthcalculator.feature.database.impl.entity.AccountEntity
import com.rosenstefanov.networthcalculator.feature.database.impl.entity.AccountValueEntity
import com.rosenstefanov.networthcalculator.feature.database.api.model.AccountType as DomainAccountType
import com.rosenstefanov.networthcalculator.feature.database.impl.entity.AccountType as EntityAccountType

internal fun EntityAccountType.toDomain(): DomainAccountType = when (this) {
    EntityAccountType.ASSET -> DomainAccountType.ASSET
    EntityAccountType.LIABILITY -> DomainAccountType.LIABILITY
}

internal fun DomainAccountType.toEntity(): EntityAccountType = when (this) {
    DomainAccountType.ASSET -> EntityAccountType.ASSET
    DomainAccountType.LIABILITY -> EntityAccountType.LIABILITY
}

internal fun AccountEntity.toDomain(): Account = Account(
    id = id,
    type = type.toDomain(),
    name = name,
    category = category,
    value = Money.ofMinor(amountMinor, currencyCode),
    note = note,
    createdAt = Instant.ofEpochMilli(createdAt),
    updatedAt = Instant.ofEpochMilli(updatedAt),
)

internal fun AccountValueEntity.toDomain(currencyCode: String): ValuePoint = ValuePoint(
    value = Money.ofMinor(amountMinor, currencyCode),
    recordedAt = Instant.ofEpochMilli(recordedAt),
)

internal fun TypedAccountValue.toDomain(): TrendPoint = TrendPoint(
    type = type.toDomain(),
    value = Money.ofMinor(amountMinor, currencyCode),
    recordedAt = Instant.ofEpochMilli(recordedAt),
)

internal fun CurrencyTotalRow.toDomain(): CurrencyTotal = CurrencyTotal(
    type = type.toDomain(),
    total = Money.ofMinor(totalMinor, currencyCode),
)
