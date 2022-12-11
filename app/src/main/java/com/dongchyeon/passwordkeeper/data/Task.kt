package com.dongchyeon.passwordkeeper.data

sealed class Task<out R> {

    data class Success<out T>(val data: T) : Task<T>()
    data class Error(val exception: Exception) : Task<Nothing>()

    override fun toString(): String {
        return when (this) {
            is Success<*> -> "Success[data=$data]"
            is Error -> "Error[exception=$exception]"
        }
    }

    val Task<*>.succeeded
        get() = this is Task.Success && data != null
}