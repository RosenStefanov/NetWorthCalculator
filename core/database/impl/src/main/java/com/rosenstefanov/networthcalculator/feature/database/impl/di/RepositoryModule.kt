package com.rosenstefanov.networthcalculator.feature.database.impl.di

import com.rosenstefanov.networthcalculator.feature.database.api.AccountRepository
import com.rosenstefanov.networthcalculator.feature.database.impl.repository.AccountRepositoryImpl
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
abstract class RepositoryModule {

    @Binds
    @Singleton
    abstract fun bindAccountRepository(impl: AccountRepositoryImpl): AccountRepository
}
