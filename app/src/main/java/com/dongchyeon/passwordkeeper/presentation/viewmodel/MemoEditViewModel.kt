package com.dongchyeon.passwordkeeper.presentation.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.dongchyeon.passwordkeeper.data.Task
import com.dongchyeon.passwordkeeper.data.model.Memo
import com.dongchyeon.passwordkeeper.domain.GetMemoByIdUseCase
import com.dongchyeon.passwordkeeper.domain.InsertMemoUseCase
import com.dongchyeon.passwordkeeper.domain.UpdateMemoUseCase
import com.dongchyeon.passwordkeeper.presentation.base.BaseViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class MemoEditViewModel @Inject constructor(
    private val getMemoByIdUseCase: GetMemoByIdUseCase,
    private val insertMemoUseCase: InsertMemoUseCase,
    private val updateMemoUseCase: UpdateMemoUseCase
) : BaseViewModel() {
    private lateinit var memoItem: Memo
    private val _memo = MutableLiveData<Memo>()
    val memo: LiveData<Memo> get() = _memo

    private val _isUpserted = MutableLiveData<Boolean>(false)
    val isUpserted: LiveData<Boolean> get() = _isUpserted

    fun loadMemo(id: Long) = viewModelScope.launch {
        getMemoByIdUseCase(id).collect {
            if (it is Task.Success<Memo>) {
                memoItem = it.data
                _memo.postValue(memoItem)
            }
        }
    }

    fun insert(
        title: String,
        category: String,
        uid: String,
        password: String,
        memoTxt: String
    ) = viewModelScope.launch {
        val task = insertMemoUseCase(
            title,
            category,
            uid,
            password,
            memoTxt
        )
        if (task is Task.Success<String>) {
            _toastMessage.postValue(task.data)
            _isUpserted.postValue(true)
        } else if (task is Task.Error) {
            _toastMessage.postValue(task.exception.message)
        }
    }

    fun update(
        id: Long,
        title: String,
        category: String,
        uid: String,
        password: String,
        memoTxt: String
    ) = viewModelScope.launch {
        val task = updateMemoUseCase(
            id,
            title,
            category,
            uid,
            password,
            memoTxt
        )
        if (task is Task.Success) {
            _toastMessage.postValue(task.data)
            _isUpserted.postValue(true)
        } else if (task is Task.Error) {
            _toastMessage.postValue(task.exception.message)
        }
    }
}