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
import com.dongchyeon.passwordkeeper.util.Event
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class MemoEditViewModel @Inject constructor(
    private val getMemoByIdUseCase: GetMemoByIdUseCase,
    private val insertMemoUseCase: InsertMemoUseCase,
    private val updateMemoUseCase: UpdateMemoUseCase
) : BaseViewModel() {
    private var memoItem: Memo = Memo("", "", "", "", "")
    private val _memo = MutableLiveData<Memo>()
    val memo: LiveData<Memo> get() = _memo

    private val _insertedId = MutableLiveData<Event<Long>>(Event(-1L))
    val insertedId: LiveData<Event<Long>> get() = _insertedId
    private val _isUpdated = MutableLiveData<Event<Boolean>>(Event(false))
    val isUpdated: LiveData<Event<Boolean>> get() = _isUpdated

    fun loadMemo(id: Long) = viewModelScope.launch {
        val memo = getMemoByIdUseCase(id)
        if (memo is Task.Success<Memo>) {
            memoItem = memo.data
            _memo.postValue(memoItem)
        } else {
            memoItem = Memo("", "", "", "", "")
            _memo.postValue(memoItem)
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
        if (task is Task.Success<Long>) {
            _toastMessage.postValue("추가되었습니다")
            _insertedId.value = Event(task.data)
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
            _isUpdated.value = Event(true)
        } else if (task is Task.Error) {
            _toastMessage.postValue(task.exception.message)
        }
    }
}