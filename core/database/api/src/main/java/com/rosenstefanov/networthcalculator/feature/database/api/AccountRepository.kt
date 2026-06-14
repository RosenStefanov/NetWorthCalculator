package com.rosenstefanov.networthcalculator.feature.database.api

import com.rosenstefanov.networthcalculator.feature.database.api.model.Account
import com.rosenstefanov.networthcalculator.feature.database.api.model.AccountType
import com.rosenstefanov.networthcalculator.feature.database.api.model.CurrencyTotal
import com.rosenstefanov.networthcalculator.feature.database.api.model.NewAccount
import com.rosenstefanov.networthcalculator.feature.database.api.model.TrendPoint
import com.rosenstefanov.networthcalculator.feature.database.api.model.ValuePoint
import kotlinx.coroutines.flow.Flow
import java.time.Instant

interface AccountRepository {

    fun observeAccounts(): Flow<List<Account>>

    fun observeAccountsByType(type: AccountType): Flow<List<Account>>

    fun observeAccount(id: Long): Flow<Account?>

    fun observeAccountCount(): Flow<Int>

    suspend fun getAccount(id: Long): Account?

    suspend fun addAccount(account: NewAccount): Long

    suspend fun updateAccount(account: Account)

    suspend fun deleteAccount(id: Long)

    fun observeHistory(accountId: Long): Flow<List<ValuePoint>>

    fun observeHistorySince(accountId: Long, since: Instant): Flow<List<ValuePoint>>

    fun observeTotalsByCurrency(): Flow<List<CurrencyTotal>>

    fun observeTrendSince(since: Instant): Flow<List<TrendPoint>>
}
