package com.dongchyeon.passwordkeeper.data.datasource

import android.content.Context
import javax.inject.Inject

class AuthDataSource @Inject constructor(
    private val context: Context
) {
    private val pref = context.getSharedPreferences("pref", Context.MODE_PRIVATE)

    fun getPassword(): String =
        pref.getString("password", "").toString()

    fun setPassword(password: String) =
        pref.edit().let {
            it.putString("password", password)
            it.apply()
        }

    fun getIsRegistered(): Boolean =
        pref.getBoolean("isRegistered", false)

    fun setIsRegistered(isRegistered: Boolean) =
        pref.edit().let {
            it.putBoolean("isRegistered", isRegistered)
            it.apply()
        }

}