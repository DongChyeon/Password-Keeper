package com.dongchyeon.passwordkeeper.data.repository

import com.dongchyeon.passwordkeeper.data.datasource.MemoDataSource
import com.dongchyeon.passwordkeeper.data.model.Memo
import javax.inject.Inject

class MemoRepository @Inject constructor(
    private val memoDataSource: MemoDataSource
) {
    fun getAllMemos() =
        memoDataSource.getAllMemos()

    fun getMemoById(id: Long) =
        memoDataSource.getMemoById(id)

    suspend fun insertMemo(memo: Memo) =
        memoDataSource.insertMemo(memo)

    suspend fun updateMemo(memo: Memo) =
        memoDataSource.updateMemo(memo)

    suspend fun deleteMemo(memo: Memo) =
        memoDataSource.deleteMemo(memo)

    fun getMemosByCategory(category: String) =
        memoDataSource.getMemosByCategory(category)

    fun getAllCategories() =
        memoDataSource.getAllCategories()
}