package com.dongchyeon.passwordkeeper.presentation.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.dongchyeon.passwordkeeper.data.Task
import com.dongchyeon.passwordkeeper.domain.GetIsRegisteredUseCase
import com.dongchyeon.passwordkeeper.domain.LoginUseCase
import com.dongchyeon.passwordkeeper.presentation.base.BaseViewModel
import com.dongchyeon.passwordkeeper.util.Event
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class LoginViewModel @Inject constructor(
    private val loginUseCase: LoginUseCase,
    private val getIsRegisteredUseCase: GetIsRegisteredUseCase
) : BaseViewModel() {
    private val _isRegisterd = MutableLiveData<Event<Boolean>>(Event(getIsRegisteredUseCase()))
    val isRegistered: LiveData<Event<Boolean>> get() = _isRegisterd
    private val _isLoggedIn = MutableLiveData<Event<Boolean>>(Event(false))
    val isLoggedIn: LiveData<Event<Boolean>> get() = _isLoggedIn

    fun login(password: String) {
        val task = loginUseCase(password)
        if (task is Task.Success<String>) {
            _toastMessage.postValue(task.data)
            _isLoggedIn.value = Event(true)
        } else if (task is Task.Error) {
            _toastMessage.postValue(task.exception.message)
        }
    }
}