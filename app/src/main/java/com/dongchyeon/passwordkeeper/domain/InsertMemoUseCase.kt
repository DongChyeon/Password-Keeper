package com.dongchyeon.passwordkeeper.domain

import com.dongchyeon.passwordkeeper.data.repository.MemoRepository
import javax.inject.Inject

class InsertMemoUseCase @Inject constructor(
    private val memoRepository: MemoRepository
) {
    suspend operator fun invoke(
        title: String,
        category: String,
        uid: String,
        password: String,
        memoTxt: String
    ) = memoRepository.insertMemo(
        title,
        category,
        uid,
        password,
        memoTxt
    )
}