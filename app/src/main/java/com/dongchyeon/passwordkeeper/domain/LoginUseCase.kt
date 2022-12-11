package com.dongchyeon.passwordkeeper.domain

import com.dongchyeon.passwordkeeper.data.repository.AuthRepository

class LoginUseCase constructor(
    private val authRepository: AuthRepository
) {
    operator fun invoke(password: String) =
        authRepository.login(password)
}