package com.dongchyeon.passwordkeeper.data.pref

import android.content.Context

class SharedPrefStorage(val context: Context) {
    private val pref = context.getSharedPreferences("pref", Context.MODE_PRIVATE)

    var password: String
        get() {
            return pref.getString("password", "").toString()
        }
        set(value) {
            pref.edit().let {
                it.putString("password", value)
                it.apply()
            }
        }

    var isRegistered: Boolean
        get() {
            return pref.getBoolean("isRegistered", false)
        }
        set(value) {
            pref.edit().let {
                it.putBoolean("isRegistered", value)
                it.apply()
            }
        }
}