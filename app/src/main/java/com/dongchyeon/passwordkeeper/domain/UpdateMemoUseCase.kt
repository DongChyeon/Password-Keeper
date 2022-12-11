package com.dongchyeon.passwordkeeper.domain

import com.dongchyeon.passwordkeeper.data.model.Memo
import com.dongchyeon.passwordkeeper.data.repository.MemoRepository
import javax.inject.Inject

class UpdateMemoUseCase @Inject constructor(
    private val memoRepository: MemoRepository
) {
    suspend operator fun invoke(memo: Memo) =
        memoRepository.updateMemo(memo)
}