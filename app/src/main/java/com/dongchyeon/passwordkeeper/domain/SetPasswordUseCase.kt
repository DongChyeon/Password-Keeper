package com.dongchyeon.passwordkeeper.domain

import com.dongchyeon.passwordkeeper.data.Task
import com.dongchyeon.passwordkeeper.data.repository.AuthRepository
import javax.inject.Inject

class SetPasswordUseCase @Inject constructor(
    private val authRepository: AuthRepository
) {
    operator fun invoke(pw: String, confirmPw: String): Task<String> {
        return authRepository.setPassword(pw, confirmPw)
    }
}