package com.rosenstefanov.networthcalculator.feature.database.impl.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import com.rosenstefanov.networthcalculator.feature.database.impl.entity.AccountValueEntity
import kotlinx.coroutines.flow.Flow

@Dao
interface AccountValueDao {

    @Insert
    suspend fun insert(value: AccountValueEntity): Long

    @Query("SELECT * FROM account_values WHERE account_id = :accountId ORDER BY recorded_at ASC")
    fun observeForAccount(accountId: Long): Flow<List<AccountValueEntity>>

    @Query(
        "SELECT * FROM account_values WHERE account_id = :accountId AND recorded_at >= :since " +
            "ORDER BY recorded_at ASC",
    )
    fun observeForAccountSince(accountId: Long, since: Long): Flow<List<AccountValueEntity>>

    @Query("SELECT * FROM account_values WHERE account_id = :accountId ORDER BY recorded_at DESC LIMIT 1")
    suspend fun latestForAccount(accountId: Long): AccountValueEntity?

    @Query(
        "SELECT a.type AS type, v.amount_minor AS amountMinor, v.recorded_at AS recordedAt, " +
            "a.currency_code AS currencyCode " +
            "FROM account_values v INNER JOIN accounts a ON a.id = v.account_id " +
            "WHERE v.recorded_at >= :since ORDER BY v.recorded_at ASC",
    )
    fun observeTypedValuesSince(since: Long): Flow<List<TypedAccountValue>>
}
