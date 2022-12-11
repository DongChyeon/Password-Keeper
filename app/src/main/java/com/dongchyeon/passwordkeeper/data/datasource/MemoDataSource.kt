package com.dongchyeon.passwordkeeper.data.datasource

import com.dongchyeon.passwordkeeper.data.Task
import com.dongchyeon.passwordkeeper.data.model.Memo
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject

class MemoDataSource @Inject constructor(
    private val memoDao: MemoDao,
) {
    fun getAllMemos(): Flow<Task<List<Memo>>> =
        memoDao.getAllMemos().map {
            Task.Success(it)
        }

    fun getMemoById(id: Long): Flow<Task<Memo>> =
        memoDao.getMemoById(id).map {
            Task.Success(it)
        }

    suspend fun insertMemo(memo: Memo) =
        memoDao.insertMemo(memo)

    suspend fun updateMemoById(
        id: Long,
        title: String,
        category: String,
        uid: String,
        password: String,
        memo: String
    ) = memoDao.updateMemoById(id, title, category, uid, password, memo)

    suspend fun deleteMemo(memo: Memo) =
        memoDao.deleteMemo(memo)

    fun getMemosByCategory(category: String): Flow<Task<List<Memo>>> =
        memoDao.getMemosByCategory(category).map {
            Task.Success(it)
        }

    fun getAllCategories(): Flow<Task<List<String>>> =
        memoDao.getAllCategories().map {
            Task.Success(it)
        }
}