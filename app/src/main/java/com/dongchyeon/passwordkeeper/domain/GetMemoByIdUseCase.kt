package com.dongchyeon.passwordkeeper.domain

import com.dongchyeon.passwordkeeper.data.repository.MemoRepository
import javax.inject.Inject

class GetMemoByIdUseCase @Inject constructor(
    private val memoRepository: MemoRepository
) {
    operator fun invoke(id: Long) =
        memoRepository.getMemoById(id)
}