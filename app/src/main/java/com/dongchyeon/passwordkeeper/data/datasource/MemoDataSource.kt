package com.dongchyeon.passwordkeeper.data.datasource

import com.dongchyeon.passwordkeeper.data.model.Memo
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class MemoDataSource @Inject constructor(
    private val memoDao: MemoDao,
) {
    fun getAllMemos(): Flow<List<Memo>> =
        memoDao.getAllMemos()

    fun getMemoById(id: Long): Flow<Memo> =
        memoDao.getMemoById(id)

    suspend fun insertMemo(memo: Memo) =
        memoDao.insertMemo(memo)

    suspend fun updateMemo(memo: Memo) =
        memoDao.updateMemo(memo)

    suspend fun deleteMemo(memo: Memo) =
        memoDao.deleteMemo(memo)

    fun getMemosByCategory(category: String) =
        memoDao.getMemosByCategory(category)

    fun getAllCategories() =
        memoDao.getAllCategories()
}