package com.dongchyeon.passwordkeeper.data.repository

import com.dongchyeon.passwordkeeper.data.Task
import com.dongchyeon.passwordkeeper.data.datasource.AuthDataSource
import javax.inject.Inject

class AuthRepository @Inject constructor(
    private val authDataSource: AuthDataSource
) {
    fun setPassword(pw: String, confirmPw: String): Task<String> {
        return if (pw == confirmPw) {
            if (!authDataSource.getIsRegistered()) authDataSource.setIsRegistered(true)
            authDataSource.setPassword(pw)
            Task.Success("비밀번호가 설정되었습니다")
        } else {
            Task.Error(Exception("두 비밀번호가 일치하지 않습니다"))
        }
    }

    fun login(password: String): Task<String> {
        return if (authDataSource.getPassword() == password) {
            Task.Success("로그인에 성공했습니다")
        } else {
            Task.Error(Exception("비밀번호가 일치하지 않습니다"))
        }
    }

    fun getIsRegistered() =
        authDataSource.getIsRegistered()
}