package com.dongchyeon.passwordkeeper.presentation.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.dongchyeon.passwordkeeper.data.Task
import com.dongchyeon.passwordkeeper.data.model.Memo
import com.dongchyeon.passwordkeeper.domain.DeleteMemoUseCase
import com.dongchyeon.passwordkeeper.domain.GetMemoByIdUseCase
import com.dongchyeon.passwordkeeper.presentation.base.BaseViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class MemoDetailViewModel @Inject constructor(
    private val getMemoByIdUseCase: GetMemoByIdUseCase,
    private val deleteMemoUseCase: DeleteMemoUseCase
) : BaseViewModel() {
    private lateinit var memoItem: Memo
    private val _memo = MutableLiveData<Memo>()
    val memo: LiveData<Memo> get() = _memo

    private val _isDeleted = MutableLiveData<Boolean>(false)
    val isDeleted: LiveData<Boolean> get() = _isDeleted

    fun loadMemo(id: Long) = viewModelScope.launch {
        getMemoByIdUseCase(id).collect {
            if (it is Task.Success<Memo>) {
                memoItem = it.data
                _memo.postValue(memoItem)
            }
        }
    }

    fun delete(memo: Memo) = viewModelScope.launch {
        deleteMemoUseCase(memo)
        _toastMessage.postValue("삭제되었습니다")
        _isDeleted.postValue(true)
    }
}