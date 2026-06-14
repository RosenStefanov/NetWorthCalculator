package com.rosenstefanov.networthcalculator.feature.database.impl

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.rosenstefanov.networthcalculator.feature.database.impl.dao.AccountDao
import com.rosenstefanov.networthcalculator.feature.database.impl.dao.AccountValueDao
import com.rosenstefanov.networthcalculator.feature.database.impl.entity.AccountEntity
import com.rosenstefanov.networthcalculator.feature.database.impl.entity.AccountValueEntity
import com.rosenstefanov.networthcalculator.feature.database.impl.entity.Converters

@Database(
    entities = [
        AccountEntity::class,
        AccountValueEntity::class,
    ],
    version = 1,
    exportSchema = false,
)
@TypeConverters(Converters::class)
abstract class NetWorthDatabase : RoomDatabase() {
    abstract fun accountDao(): AccountDao
    abstract fun accountValueDao(): AccountValueDao

    companion object {
        const val NAME = "networth.db"
    }
}
