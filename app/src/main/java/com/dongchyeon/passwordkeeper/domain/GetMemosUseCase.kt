package com.dongchyeon.passwordkeeper.domain

import com.dongchyeon.passwordkeeper.data.repository.MemoRepository
import javax.inject.Inject

class GetMemosUseCase @Inject constructor(
    private val memoRepository: MemoRepository
) {
    operator fun invoke() =
        memoRepository.getAllMemos()
}