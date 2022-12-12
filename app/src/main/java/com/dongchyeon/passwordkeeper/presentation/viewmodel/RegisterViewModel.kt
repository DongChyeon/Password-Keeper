package com.dongchyeon.passwordkeeper.presentation.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.dongchyeon.passwordkeeper.data.Task
import com.dongchyeon.passwordkeeper.domain.SetPasswordUseCase
import com.dongchyeon.passwordkeeper.presentation.base.BaseViewModel
import com.dongchyeon.passwordkeeper.util.Event
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class RegisterViewModel @Inject constructor(
    private val setPasswordUseCase: SetPasswordUseCase
) : BaseViewModel() {
    private val _isPasswordSet = MutableLiveData<Event<Boolean>>(Event(false))
    val isPasswordSet: LiveData<Event<Boolean>> get() = _isPasswordSet

    fun setPassword(pw: String, confirmPw: String) {
        val task = setPasswordUseCase(pw, confirmPw)
        if (task is Task.Success<String>) {
            _toastMessage.postValue(task.data)
            _isPasswordSet.value = Event(true)
        } else if (task is Task.Error) {
            _toastMessage.postValue(task.exception.message)
        }
    }
}