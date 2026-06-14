package com.rosenstefanov.networthcalculator.feature.database.impl.repository

import android.content.Context
import androidx.room.Room
import androidx.test.core.app.ApplicationProvider
import com.google.common.truth.Truth.assertThat
import com.rosenstefanov.networthcalculator.feature.database.api.model.AccountType
import com.rosenstefanov.networthcalculator.feature.database.api.model.Money
import com.rosenstefanov.networthcalculator.feature.database.api.model.NewAccount
import com.rosenstefanov.networthcalculator.feature.database.impl.NetWorthDatabase
import com.rosenstefanov.networthcalculator.feature.database.impl.di.DatabaseModule
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.test.runTest
import org.junit.After
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import org.robolectric.RobolectricTestRunner
import org.robolectric.annotation.Config
import java.math.BigDecimal
import java.time.Clock
import java.time.Instant
import java.time.ZoneOffset

@RunWith(RobolectricTestRunner::class)
@Config(sdk = [33])
class AccountRepositoryImplTest {

    private val now = Instant.parse("2026-06-14T12:00:00Z")
    private val clock = Clock.fixed(now, ZoneOffset.UTC)

    private lateinit var db: NetWorthDatabase
    private lateinit var repo: AccountRepositoryImpl

    @Before
    fun setUp() {
        val context = ApplicationProvider.getApplicationContext<Context>()
        db = Room.inMemoryDatabaseBuilder(context, NetWorthDatabase::class.java)
            .allowMainThreadQueries()
            .build()
        repo = AccountRepositoryImpl(db, db.accountDao(), db.accountValueDao(), clock)
    }

    @After
    fun tearDown() {
        db.close()
    }

    private fun money(value: String, code: String = "USD") = Money(BigDecimal(value), code)

    private suspend fun addAsset(name: String, value: String, code: String = "USD"): Long =
        repo.addAccount(NewAccount(AccountType.ASSET, name, "INVESTMENTS", money(value, code)))

    @Test
    fun addAccount_storesAccount_andSeedsHistory() = runTest {
        val id = addAsset("Brokerage", "1234.56")

        val account = repo.getAccount(id)!!
        assertThat(account.name).isEqualTo("Brokerage")
        assertThat(account.type).isEqualTo(AccountType.ASSET)
        assertThat(account.value.currencyCode).isEqualTo("USD")
        assertThat(account.value.amount.compareTo(BigDecimal("1234.56"))).isEqualTo(0)
        // Timestamps come from the injected clock — deterministic.
        assertThat(account.createdAt).isEqualTo(now)
        assertThat(account.updatedAt).isEqualTo(now)

        val history = repo.observeHistory(id).first()
        assertThat(history).hasSize(1)
        assertThat(history.single().recordedAt).isEqualTo(now)
        assertThat(repo.observeAccounts().first()).hasSize(1)
        assertThat(repo.observeAccountCount().first()).isEqualTo(1)
        assertThat(repo.observeAccount(id).first()?.id).isEqualTo(id)
    }

    @Test
    fun updateAccount_withNewValue_appendsSnapshot() = runTest {
        val id = addAsset("Brokerage", "1000.00")
        val account = repo.getAccount(id)!!

        repo.updateAccount(account.copy(value = money("2000.00")))

        assertThat(repo.getAccount(id)!!.value.amount.compareTo(BigDecimal("2000.00"))).isEqualTo(0)
        assertThat(repo.observeHistory(id).first()).hasSize(2)
    }

    @Test
    fun updateAccount_nameOnly_doesNotAppendSnapshot() = runTest {
        val id = addAsset("Brokerage", "1000.00")
        val account = repo.getAccount(id)!!

        repo.updateAccount(account.copy(name = "Renamed"))

        assertThat(repo.getAccount(id)!!.name).isEqualTo("Renamed")
        assertThat(repo.observeHistory(id).first()).hasSize(1)
    }

    @Test
    fun updateAccount_missingAccount_isNoOp() = runTest {
        repo.updateAccount(
            com.rosenstefanov.networthcalculator.feature.database.api.model.Account(
                id = 999,
                type = AccountType.ASSET,
                name = "Ghost",
                category = "OTHER",
                value = money("10.00"),
                note = null,
                createdAt = Instant.EPOCH,
                updatedAt = Instant.EPOCH,
            ),
        )
        assertThat(repo.observeAccountCount().first()).isEqualTo(0)
    }

    @Test
    fun deleteAccount_cascadesHistory() = runTest {
        val id = addAsset("Brokerage", "1000.00")

        repo.deleteAccount(id)

        assertThat(repo.getAccount(id)).isNull()
        assertThat(repo.observeHistory(id).first()).isEmpty()
        assertThat(repo.observeAccountCount().first()).isEqualTo(0)
    }

    @Test
    fun observeAccountsByType_filtersByType() = runTest {
        addAsset("Brokerage", "1000.00")
        repo.addAccount(NewAccount(AccountType.LIABILITY, "Card", "CREDIT_CARD", money("200.00")))

        val assets = repo.observeAccountsByType(AccountType.ASSET).first()
        assertThat(assets).hasSize(1)
        assertThat(assets.single().type).isEqualTo(AccountType.ASSET)
    }

    @Test
    fun observeTotalsByCurrency_groupsByTypeAndCurrency() = runTest {
        addAsset("A", "100.00", "USD")
        addAsset("B", "50.00", "USD")
        addAsset("C", "30.00", "EUR")

        val totals = repo.observeTotalsByCurrency().first()
        val usdAssets = totals.single { it.type == AccountType.ASSET && it.total.currencyCode == "USD" }
        assertThat(usdAssets.total.amount.compareTo(BigDecimal("150.00"))).isEqualTo(0)
        assertThat(totals.any { it.total.currencyCode == "EUR" }).isTrue()
    }

    @Test
    fun observeTrendSince_returnsTypedPoints_andRespectsWindow() = runTest {
        val id = addAsset("Brokerage", "1000.00")

        val all = repo.observeTrendSince(Instant.EPOCH).first()
        assertThat(all).hasSize(1)
        assertThat(all.single().type).isEqualTo(AccountType.ASSET)

        val future = now.plusSeconds(1)
        assertThat(repo.observeTrendSince(future).first()).isEmpty()
        assertThat(repo.observeHistorySince(id, Instant.EPOCH).first()).hasSize(1)
        assertThat(repo.observeHistorySince(id, future).first()).isEmpty()
    }

    @Test
    fun databaseModule_providesWorkingGraph() {
        val context = ApplicationProvider.getApplicationContext<Context>()
        context.deleteDatabase(NetWorthDatabase.NAME)
        val real = DatabaseModule.provideDatabase(context)
        try {
            assertThat(DatabaseModule.provideAccountDao(real)).isNotNull()
            assertThat(DatabaseModule.provideAccountValueDao(real)).isNotNull()
        } finally {
            real.close()
            context.deleteDatabase(NetWorthDatabase.NAME)
        }
    }
}
