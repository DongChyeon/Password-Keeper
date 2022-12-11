package com.dongchyeon.passwordkeeper.data.datasource

import androidx.room.Database
import androidx.room.RoomDatabase
import com.dongchyeon.passwordkeeper.data.model.Memo

@Database(entities = [Memo::class], version = 1, exportSchema = false)
abstract class AppDatabase : RoomDatabase() {
    abstract fun memosDao(): MemoDao
}