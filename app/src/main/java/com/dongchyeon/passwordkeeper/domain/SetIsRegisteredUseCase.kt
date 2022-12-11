package com.dongchyeon.passwordkeeper.domain

import com.dongchyeon.passwordkeeper.data.repository.AuthRepository
import javax.inject.Inject

class SetIsRegisteredUseCase @Inject constructor(
    private val authRepository: AuthRepository
) {
    operator fun invoke(isRegistered: Boolean) =
        authRepository.setIsRegistered(isRegistered)
}