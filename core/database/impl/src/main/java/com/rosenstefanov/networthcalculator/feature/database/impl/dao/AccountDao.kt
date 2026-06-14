package com.rosenstefanov.networthcalculator.feature.database.impl.dao

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Update
import com.rosenstefanov.networthcalculator.feature.database.impl.entity.AccountEntity
import com.rosenstefanov.networthcalculator.feature.database.impl.entity.AccountType
import kotlinx.coroutines.flow.Flow

@Dao
interface AccountDao {

    @Insert
    suspend fun insert(account: AccountEntity): Long

    @Update
    suspend fun update(account: AccountEntity)

    @Delete
    suspend fun delete(account: AccountEntity)

    @Query("DELETE FROM accounts WHERE id = :id")
    suspend fun deleteById(id: Long)

    @Query("SELECT * FROM accounts WHERE id = :id")
    suspend fun getById(id: Long): AccountEntity?

    @Query("SELECT * FROM accounts WHERE id = :id")
    fun observeById(id: Long): Flow<AccountEntity?>

    @Query("SELECT * FROM accounts ORDER BY name COLLATE NOCASE ASC")
    fun observeAll(): Flow<List<AccountEntity>>

    @Query("SELECT * FROM accounts WHERE type = :type ORDER BY amount_minor DESC")
    fun observeByType(type: AccountType): Flow<List<AccountEntity>>

    @Query("SELECT COUNT(*) FROM accounts")
    fun observeCount(): Flow<Int>

    @Query(
        "SELECT type AS type, currency_code AS currencyCode, SUM(amount_minor) AS totalMinor " +
            "FROM accounts GROUP BY type, currency_code",
    )
    fun observeCurrencyTotals(): Flow<List<CurrencyTotal>>
}
