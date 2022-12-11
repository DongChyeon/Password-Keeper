package com.dongchyeon.passwordkeeper.data.repository

import com.dongchyeon.passwordkeeper.data.datasource.AuthDataSource
import javax.inject.Inject

class AuthRepository @Inject constructor(
    private val authDataSource: AuthDataSource
) {
    fun getPassword() =
        authDataSource.getPassword()

    fun setPassword(password: String) =
        authDataSource.setPassword(password)

    fun getIsRegistered() =
        authDataSource.getIsRegistered()

    fun setIsRegistered(isRegistered: Boolean) =
        authDataSource.setIsRegistered(isRegistered)
}