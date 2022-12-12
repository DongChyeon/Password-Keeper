package com.dongchyeon.passwordkeeper.presentation.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.dongchyeon.passwordkeeper.data.Task
import com.dongchyeon.passwordkeeper.data.model.Memo
import com.dongchyeon.passwordkeeper.domain.DeleteMemoUseCase
import com.dongchyeon.passwordkeeper.domain.GetMemoByIdUseCase
import com.dongchyeon.passwordkeeper.presentation.base.BaseViewModel
import com.dongchyeon.passwordkeeper.util.Event
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class MemoDetailViewModel @Inject constructor(
    private val getMemoByIdUseCase: GetMemoByIdUseCase,
    private val deleteMemoUseCase: DeleteMemoUseCase
) : BaseViewModel() {
    private var memoItem: Memo = Memo("", "", "", "", "")
    private val _memo = MutableLiveData<Memo>()
    val memo: LiveData<Memo> get() = _memo

    private val _isDeleted = MutableLiveData<Event<Boolean>>(Event(false))
    val isDeleted: LiveData<Event<Boolean>> get() = _isDeleted

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

    fun delete(memo: Memo) = viewModelScope.launch {
        deleteMemoUseCase(memo)
        _toastMessage.postValue("삭제되었습니다")
        _isDeleted.value = Event(true)
    }
}