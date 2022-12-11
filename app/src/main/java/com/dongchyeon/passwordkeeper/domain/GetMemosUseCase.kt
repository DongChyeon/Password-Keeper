package com.dongchyeon.passwordkeeper.domain

import com.dongchyeon.passwordkeeper.data.model.Memo
import com.dongchyeon.passwordkeeper.data.repository.MemoRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class GetMemosUseCase @Inject constructor(
    private val memoRepository: MemoRepository
) {
    operator fun invoke(): Flow<List<Memo>> =
        memoRepository.getAllMemos()
}