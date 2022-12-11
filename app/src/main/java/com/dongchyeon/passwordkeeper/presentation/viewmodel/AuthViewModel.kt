package com.dongchyeon.passwordkeeper.presentation.viewmodel

import androidx.lifecycle.ViewModel
import com.dongchyeon.passwordkeeper.domain.GetIsRegisteredUseCase
import com.dongchyeon.passwordkeeper.domain.GetPasswordUseCase
import com.dongchyeon.passwordkeeper.domain.SetIsRegisteredUseCase
import com.dongchyeon.passwordkeeper.domain.SetPasswordUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class AuthViewModel @Inject constructor(
    private val getPasswordUseCase: GetPasswordUseCase,
    private val setPasswordUseCase: SetPasswordUseCase,
    private val getIsRegisteredUseCase: GetIsRegisteredUseCase,
    private val setIsRegisteredUseCase: SetIsRegisteredUseCase
): ViewModel() {
    var password: String
        get() {
            return getPasswordUseCase()
        }
        set(value) {
            setPasswordUseCase(value)
        }

    var isRegistered: Boolean
        get() {
            return getIsRegisteredUseCase()
        }
        set(value) {
            setIsRegisteredUseCase(value)
        }
}