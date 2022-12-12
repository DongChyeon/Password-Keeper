package com.dongchyeon.passwordkeeper.data.repository

import com.dongchyeon.passwordkeeper.data.Task
import com.dongchyeon.passwordkeeper.data.datasource.MemoDataSource
import com.dongchyeon.passwordkeeper.data.model.Memo
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class MemoRepository @Inject constructor(
    private val memoDataSource: MemoDataSource
) {
    fun getAllMemos(): Flow<Task<List<Memo>>> {
        return memoDataSource.getAllMemos()
    }

    suspend fun getMemoById(id: Long): Task<Memo> {
        return memoDataSource.getMemoById(id)
    }

    suspend fun insertMemo(
        title: String,
        category: String,
        uid: String,
        password: String,
        memo: String
    ): Task<Long> {
        return if (category == "전체보기") {
            Task.Error(Exception("불가능한 카테고리명입니다"))
        } else if (title == "") {
            Task.Error(Exception("제목은 필수 입력 항목입니다"))
        } else if (category == "") {
            Task.Error(Exception("카테고리는 필수 입력 항목입니다"))
        } else if (uid == "") {
            Task.Error(Exception("아이디는 필수 입력 항목입니다"))
        } else if (password == "") {
            Task.Error(Exception("비밀번호는 필수 입력 항목입니다"))
        } else {
            memoDataSource.insertMemo(Memo(title, category, uid, password, memo))
        }
    }

    suspend fun updateMemo(
        id: Long,
        title: String,
        category: String,
        uid: String,
        password: String,
        memo: String
    ): Task<String> {
        return if (category == "전체보기") {
            Task.Error(Exception("불가능한 카테고리명입니다"))
        } else if (title == "") {
            Task.Error(Exception("제목은 필수 입력 항목입니다"))
        } else if (category == "") {
            Task.Error(Exception("카테고리는 필수 입력 항목입니다"))
        } else if (uid == "") {
            Task.Error(Exception("아이디는 필수 입력 항목입니다"))
        } else if (password == "") {
            Task.Error(Exception("비밀번호는 필수 입력 항목입니다"))
        } else {
            memoDataSource.updateMemoById(id, title, category, uid, password, memo)
            Task.Success("수정되었습니다")
        }
    }

    suspend fun deleteMemo(memo: Memo) {
        memoDataSource.deleteMemo(memo)
    }

    fun getMemosByCategory(category: String): Flow<Task<List<Memo>>> {
        return memoDataSource.getMemosByCategory(category)
    }

    fun getAllCategories(): Flow<Task<List<String>>> {
        return memoDataSource.getAllCategories()
    }
}