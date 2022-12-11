package com.dongchyeon.passwordkeeper.domain

import com.dongchyeon.passwordkeeper.data.repository.AuthRepository

class GetPasswordUseCase constructor(
    private val authRepository: AuthRepository
) {
    operator fun invoke() =
        authRepository.getPassword()
}