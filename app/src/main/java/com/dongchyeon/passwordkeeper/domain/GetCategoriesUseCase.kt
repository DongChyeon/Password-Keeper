package com.dongchyeon.passwordkeeper.domain

import com.dongchyeon.passwordkeeper.data.repository.MemoRepository
import javax.inject.Inject

class GetCategoriesUseCase @Inject constructor(
    private val memoRepository: MemoRepository
) {
    operator fun invoke() =
        memoRepository.getAllCategories()
}