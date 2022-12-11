package com.dongchyeon.passwordkeeper.domain

import com.dongchyeon.passwordkeeper.data.repository.AuthRepository
import javax.inject.Inject

class SetPasswordUseCase @Inject constructor(
    private val authRepository: AuthRepository
) {
    operator fun invoke(password: String) =
        authRepository.setPassword(password)
}