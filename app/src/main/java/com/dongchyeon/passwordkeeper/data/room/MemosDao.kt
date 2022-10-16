package com.dongchyeon.passwordkeeper.data.room

import androidx.room.*
import kotlinx.coroutines.flow.Flow

@Dao
interface MemosDao {
    @Query("SELECT * FROM Memos")
    fun getAllMemos(): Flow<List<Memo>>

    @Insert
    suspend fun insertMemo(memo: Memo)

    @Update
    suspend fun updateMemo(memo: Memo)

    @Delete
    suspend fun deleteMemo(memo: Memo)

    @Query("SELECT * FROM Memos WHERE id = :id")
    suspend fun getMemoById(id: Long): Memo

    @Query("SELECT * FROM Memos WHERE category = :category")
    fun getMemosByCategory(category: String): Flow<List<Memo>>

    @Query("SELECT DISTINCT category FROM Memos")
    fun getAllCategories(): Flow<List<String>>
}