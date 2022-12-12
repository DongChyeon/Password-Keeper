package com.dongchyeon.passwordkeeper.domain

import com.dongchyeon.passwordkeeper.data.Task
import com.dongchyeon.passwordkeeper.data.model.Memo
import com.dongchyeon.passwordkeeper.data.repository.MemoRepository
import javax.inject.Inject

class GetMemoByIdUseCase @Inject constructor(
    private val memoRepository: MemoRepository
) {
    suspend operator fun invoke(id: Long): Task<Memo> {
        return memoRepository.getMemoById(id)
    }
}