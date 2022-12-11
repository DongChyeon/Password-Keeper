package com.dongchyeon.passwordkeeper.domain

import com.dongchyeon.passwordkeeper.data.repository.MemoRepository
import javax.inject.Inject

class UpdateMemoUseCase @Inject constructor(
    private val memoRepository: MemoRepository
) {
    suspend operator fun invoke(
        id: Long,
        title: String,
        category: String,
        uid: String,
        password: String,
        memoTxt: String
    ) = memoRepository.updateMemo(
        id,
        title,
        category,
        uid,
        password,
        memoTxt
    )
}