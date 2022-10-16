package com.dongchyeon.passwordkeeper.data.room

import androidx.room.Database
import androidx.room.RoomDatabase

@Database(entities = [Memo::class], version = 1, exportSchema = false)
abstract class AppDatabase : RoomDatabase() {
    abstract fun memosDao(): MemosDao
}