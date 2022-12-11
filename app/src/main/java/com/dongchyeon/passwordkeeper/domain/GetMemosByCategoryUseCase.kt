package com.dongchyeon.passwordkeeper.domain

import com.dongchyeon.passwordkeeper.data.repository.MemoRepository
import javax.inject.Inject

class GetMemosByCategoryUseCase @Inject constructor(
    private val memoRepository: MemoRepository
) {
    operator fun invoke(category: String) =
        memoRepository.getMemosByCategory(category)
}