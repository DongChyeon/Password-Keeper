package com.dongchyeon.passwordkeeper.presentation.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.dongchyeon.passwordkeeper.data.Task
import com.dongchyeon.passwordkeeper.domain.GetIsRegisteredUseCase
import com.dongchyeon.passwordkeeper.domain.LoginUseCase
import com.dongchyeon.passwordkeeper.presentation.base.BaseViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class LoginViewModel @Inject constructor(
    private val loginUseCase: LoginUseCase,
    private val getIsRegisteredUseCase: GetIsRegisteredUseCase
) : BaseViewModel() {
    private val _isRegisterd = MutableLiveData<Boolean>(getIsRegisteredUseCase())
    val isRegistered: LiveData<Boolean> get() = _isRegisterd
    private val _isLoggedIn = MutableLiveData<Boolean>(false)
    val isLoggedIn: LiveData<Boolean> get() = _isLoggedIn

    fun login(password: String) {
        val task = loginUseCase(password)
        if (task is Task.Success<String>) {
            _toastMessage.postValue(task.data)
            _isLoggedIn.postValue(true)
        } else if (task is Task.Error) {
            _toastMessage.postValue(task.exception.message)
        }
    }
}