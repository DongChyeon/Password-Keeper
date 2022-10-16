package com.dongchyeon.passwordkeeper.viewmodel

import androidx.lifecycle.ViewModel
import com.dongchyeon.passwordkeeper.data.pref.SharedPrefStorage
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class AuthViewModel @Inject constructor(
    private val sharedPrefStorage: SharedPrefStorage
): ViewModel() {
    var prefPassword: String
        get() {
            return sharedPrefStorage.password
        }
        set(value) {
            sharedPrefStorage.password = value
        }

    var prefIsRegistered: Boolean
        get() {
            return sharedPrefStorage.isRegistered
        }
        set(value) {
            sharedPrefStorage.isRegistered = value
        }
}