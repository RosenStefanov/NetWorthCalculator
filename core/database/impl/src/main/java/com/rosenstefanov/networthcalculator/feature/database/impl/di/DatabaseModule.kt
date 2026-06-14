package com.rosenstefanov.networthcalculator.feature.database.impl.di

import android.content.Context
import androidx.room.Room
import com.rosenstefanov.networthcalculator.feature.database.impl.NetWorthDatabase
import com.rosenstefanov.networthcalculator.feature.database.impl.dao.AccountDao
import com.rosenstefanov.networthcalculator.feature.database.impl.dao.AccountValueDao
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import java.time.Clock
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object DatabaseModule {

    @Provides
    @Singleton
    fun provideDatabase(@ApplicationContext context: Context): NetWorthDatabase =
        Room.databaseBuilder(context, NetWorthDatabase::class.java, NetWorthDatabase.NAME)
            .build()

    @Provides
    fun provideAccountDao(database: NetWorthDatabase): AccountDao = database.accountDao()

    @Provides
    fun provideAccountValueDao(database: NetWorthDatabase): AccountValueDao =
        database.accountValueDao()

    @Provides
    fun provideClock(): Clock = Clock.systemUTC()
}
