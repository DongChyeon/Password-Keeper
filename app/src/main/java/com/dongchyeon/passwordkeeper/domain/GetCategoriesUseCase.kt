package com.dongchyeon.passwordkeeper.domain

import com.dongchyeon.passwordkeeper.data.Task
import com.dongchyeon.passwordkeeper.data.repository.MemoRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class GetCategoriesUseCase @Inject constructor(
    private val memoRepository: MemoRepository
) {
    operator fun invoke(): Flow<Task<List<String>>> {
        return memoRepository.getAllCategories()
    }
}