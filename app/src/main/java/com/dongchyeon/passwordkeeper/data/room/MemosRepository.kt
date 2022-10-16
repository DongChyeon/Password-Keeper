package com.dongchyeon.passwordkeeper.data.room

import javax.inject.Inject

class MemosRepository @Inject constructor(private val memosDao: MemosDao) {
    fun getAllMemos() =
        memosDao.getAllMemos()

    suspend fun insertMemo(memo: Memo) =
        memosDao.insertMemo(memo)

    suspend fun updateMemo(memo: Memo) =
        memosDao.updateMemo(memo)

    suspend fun deleteMemo(memo: Memo) =
        memosDao.deleteMemo(memo)

    fun getMemosByCategory(category: String) =
        memosDao.getMemosByCategory(category)

    fun getAllCategories() =
        memosDao.getAllCategories()
}