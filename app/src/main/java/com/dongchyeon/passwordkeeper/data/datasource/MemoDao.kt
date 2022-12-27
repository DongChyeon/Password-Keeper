package com.dongchyeon.passwordkeeper.data.datasource

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query
import com.dongchyeon.passwordkeeper.data.model.Memo
import kotlinx.coroutines.flow.Flow

@Dao
interface MemoDao {
    @Query("SELECT * FROM Memos")
    fun getAllMemos(): Flow<List<Memo>>

    @Insert
    suspend fun insertMemo(memo: Memo): Long

    @Query("UPDATE Memos SET title = :title, category = :category, uid = :uid, password = :password, memo = :memo WHERE id = :id")
    suspend fun updateMemoById(
        id: Long,
        title: String,
        category: String,
        uid: String,
        password: String,
        memo: String
    )

    @Delete
    suspend fun deleteMemo(memo: Memo)

    @Query("SELECT * FROM Memos WHERE id = :id")
    fun getMemoById(id: Long): Memo?

    @Query("SELECT * FROM Memos WHERE category = :category")
    fun getMemosByCategory(category: String): Flow<List<Memo>>

    @Query("SELECT DISTINCT category FROM Memos")
    fun getAllCategories(): Flow<List<String>>

    @Query("SELECT * FROM Memos LIMIT :limit OFFSET :offset")
    suspend fun getPagedMemos(limit: Int, offset: Int): List<Memo>

    @Query("SELECT * FROM Memos WHERE category = :category LIMIT :limit OFFSET :offset")
    suspend fun getPagedMemosByCategory(category: String, limit: Int, offset: Int): List<Memo>
}