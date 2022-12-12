package com.dongchyeon.passwordkeeper.data.datasource

import com.dongchyeon.passwordkeeper.data.Task
import com.dongchyeon.passwordkeeper.data.model.Memo
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.withContext
import javax.inject.Inject

class MemoDataSource @Inject constructor(
    private val memoDao: MemoDao,
    private val ioDispatcher: CoroutineDispatcher = Dispatchers.IO
) {
    fun getAllMemos(): Flow<Task<List<Memo>>> {
        return memoDao.getAllMemos().map {
            Task.Success(it)
        }
    }

    suspend fun getMemoById(id: Long): Task<Memo> = withContext(ioDispatcher) {
        try {
            val memo = memoDao.getMemoById(id)
            if (memo != null) {
                return@withContext Task.Success(memo)
            } else {
                return@withContext Task.Error(Exception("없는 메모입니다"))
            }
        } catch (e: Exception) {
            return@withContext Task.Error(e)
        }
    }

    suspend fun insertMemo(memo: Memo): Task<Long> = withContext(ioDispatcher) {
        Task.Success(memoDao.insertMemo(memo))
    }

    suspend fun updateMemoById(
        id: Long,
        title: String,
        category: String,
        uid: String,
        password: String,
        memo: String
    ) = withContext(ioDispatcher) {
        memoDao.updateMemoById(id, title, category, uid, password, memo)
    }

    suspend fun deleteMemo(memo: Memo) = withContext(ioDispatcher) {
        memoDao.deleteMemo(memo)
    }

    fun getMemosByCategory(category: String): Flow<Task<List<Memo>>> {
        return memoDao.getMemosByCategory(category).map {
            Task.Success(it)
        }
    }

    fun getAllCategories(): Flow<Task<List<String>>> {
        return memoDao.getAllCategories().map {
            Task.Success(it)
        }
    }
}