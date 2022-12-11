package com.dongchyeon.passwordkeeper.domain

import com.dongchyeon.passwordkeeper.data.model.Memo
import com.dongchyeon.passwordkeeper.data.repository.MemoRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class GetMemoByIdUseCase @Inject constructor(
    private val memoRepository: MemoRepository
) {
    operator fun invoke(id: Long): Flow<Memo> =
        memoRepository.getMemoById(id)
}