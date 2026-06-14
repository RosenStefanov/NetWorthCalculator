package com.rosenstefanov.networthcalculator.feature.database.impl.repository

import androidx.room.withTransaction
import com.rosenstefanov.networthcalculator.feature.database.api.AccountRepository
import com.rosenstefanov.networthcalculator.feature.database.api.model.Account
import com.rosenstefanov.networthcalculator.feature.database.api.model.AccountType
import com.rosenstefanov.networthcalculator.feature.database.api.model.CurrencyTotal
import com.rosenstefanov.networthcalculator.feature.database.api.model.NewAccount
import com.rosenstefanov.networthcalculator.feature.database.api.model.TrendPoint
import com.rosenstefanov.networthcalculator.feature.database.api.model.ValuePoint
import com.rosenstefanov.networthcalculator.feature.database.impl.NetWorthDatabase
import com.rosenstefanov.networthcalculator.feature.database.impl.dao.AccountDao
import com.rosenstefanov.networthcalculator.feature.database.impl.dao.AccountValueDao
import com.rosenstefanov.networthcalculator.feature.database.impl.entity.AccountEntity
import com.rosenstefanov.networthcalculator.feature.database.impl.entity.AccountValueEntity
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.map
import java.time.Clock
import java.time.Instant
import javax.inject.Inject

class AccountRepositoryImpl @Inject constructor(
    private val database: NetWorthDatabase,
    private val accountDao: AccountDao,
    private val accountValueDao: AccountValueDao,
    private val clock: Clock,
) : AccountRepository {

    override fun observeAccounts(): Flow<List<Account>> =
        accountDao.observeAll().map { rows -> rows.map(AccountEntity::toDomain) }

    override fun observeAccountsByType(type: AccountType): Flow<List<Account>> =
        accountDao.observeByType(type.toEntity()).map { rows -> rows.map(AccountEntity::toDomain) }

    override fun observeAccount(id: Long): Flow<Account?> =
        accountDao.observeById(id).map { it?.toDomain() }

    override fun observeAccountCount(): Flow<Int> = accountDao.observeCount()

    override suspend fun getAccount(id: Long): Account? = accountDao.getById(id)?.toDomain()

    override suspend fun addAccount(account: NewAccount): Long = database.withTransaction {
        val now = clock.millis()
        val minor = account.value.toMinorUnits()
        val id = accountDao.insert(
            AccountEntity(
                type = account.type.toEntity(),
                name = account.name,
                category = account.category,
                amountMinor = minor,
                currencyCode = account.value.currencyCode,
                note = account.note,
                createdAt = now,
                updatedAt = now,
            ),
        )
        accountValueDao.insert(
            AccountValueEntity(accountId = id, amountMinor = minor, recordedAt = now),
        )
        id
    }

    override suspend fun updateAccount(account: Account): Unit = database.withTransaction {
        val existing = accountDao.getById(account.id) ?: return@withTransaction
        val now = clock.millis()
        val minor = account.value.toMinorUnits()
        accountDao.update(
            existing.copy(
                type = account.type.toEntity(),
                name = account.name,
                category = account.category,
                amountMinor = minor,
                currencyCode = account.value.currencyCode,
                note = account.note,
                updatedAt = now,
            ),
        )
        val valueChanged = minor != existing.amountMinor ||
            account.value.currencyCode != existing.currencyCode
        if (valueChanged) {
            accountValueDao.insert(
                AccountValueEntity(accountId = account.id, amountMinor = minor, recordedAt = now),
            )
        }
    }

    override suspend fun deleteAccount(id: Long) = accountDao.deleteById(id)

    override fun observeHistory(accountId: Long): Flow<List<ValuePoint>> =
        combine(
            accountDao.observeById(accountId),
            accountValueDao.observeForAccount(accountId),
        ) { account, values ->
            account?.let { values.map { v -> v.toDomain(account.currencyCode) } } ?: emptyList()
        }

    override fun observeHistorySince(accountId: Long, since: Instant): Flow<List<ValuePoint>> =
        combine(
            accountDao.observeById(accountId),
            accountValueDao.observeForAccountSince(accountId, since.toEpochMilli()),
        ) { account, values ->
            account?.let { values.map { v -> v.toDomain(account.currencyCode) } } ?: emptyList()
        }

    override fun observeTotalsByCurrency(): Flow<List<CurrencyTotal>> =
        accountDao.observeCurrencyTotals().map { rows -> rows.map { it.toDomain() } }

    override fun observeTrendSince(since: Instant): Flow<List<TrendPoint>> =
        accountValueDao.observeTypedValuesSince(since.toEpochMilli()).map { rows ->
            rows.map { it.toDomain() }
        }
}
