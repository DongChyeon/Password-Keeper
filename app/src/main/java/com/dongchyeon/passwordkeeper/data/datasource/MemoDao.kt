package com.dongchyeon.passwordkeeper.data.datasource

import androidx.room.*
import com.dongchyeon.passwordkeeper.data.model.Memo
import kotlinx.coroutines.flow.Flow

@Dao
interface MemoDao {
    @Query("SELECT * FROM Memos")
    fun getAllMemos(): Flow<List<Memo>>

    @Insert
    suspend fun insertMemo(memo: Memo)

    @Update
    suspend fun updateMemo(memo: Memo)

    @Delete
    suspend fun deleteMemo(memo: Memo)

    @Query("SELECT * FROM Memos WHERE id = :id")
    fun getMemoById(id: Long): Flow<Memo>

    @Query("SELECT * FROM Memos WHERE category = :category")
    fun getMemosByCategory(category: String): Flow<List<Memo>>

    @Query("SELECT DISTINCT category FROM Memos")
    fun getAllCategories(): Flow<List<String>>
}