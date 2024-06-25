package com.bayu.narutoapp.data.local

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.bayu.narutoapp.data.local.dao.HeroDao
import com.bayu.narutoapp.data.local.dao.HeroRemoteKeysDao
import com.bayu.narutoapp.domain.model.Hero
import com.bayu.narutoapp.domain.model.HeroRemoteKeys

@Database(
    entities = [Hero::class, HeroRemoteKeys::class],
    version = 1,
    exportSchema = true,
)
@TypeConverters(DatabaseConverter::class)
abstract class NarutoDatabase : RoomDatabase() {

    abstract fun heroDao(): HeroDao

    abstract fun heroRemoteKeysDao(): HeroRemoteKeysDao

    companion object {
        fun create(context: Context, useInMemory: Boolean): NarutoDatabase {
            val databaseBuilder = if (useInMemory) {
                Room.inMemoryDatabaseBuilder(context, NarutoDatabase::class.java)
            } else {
                Room.databaseBuilder(context, NarutoDatabase::class.java, "test_db")
            }

            return databaseBuilder
                .fallbackToDestructiveMigration()
                .build()
        }
    }

}