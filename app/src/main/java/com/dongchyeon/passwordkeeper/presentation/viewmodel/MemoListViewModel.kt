package com.dongchyeon.passwordkeeper.presentation.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.dongchyeon.passwordkeeper.data.Task
import com.dongchyeon.passwordkeeper.data.model.Memo
import com.dongchyeon.passwordkeeper.domain.GetMemosByCategoryUseCase
import com.dongchyeon.passwordkeeper.domain.GetMemosUseCase
import com.dongchyeon.passwordkeeper.presentation.base.BaseViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class MemoListViewModel @Inject constructor(
    private val getMemosUseCase: GetMemosUseCase,
    private val getMemosByCategoryUseCase: GetMemosByCategoryUseCase
) : BaseViewModel() {
    private lateinit var memoList: List<Memo>
    private val _memos = MutableLiveData<List<Memo>>()
    val memos: LiveData<List<Memo>> get() = _memos

    fun loadAllMemos() = viewModelScope.launch {
        getMemosUseCase().collect {
            if (it is Task.Success<List<Memo>>) {
                memoList = it.data
                _memos.postValue(memoList)
            }
        }
    }

    fun loadMemosByCategory(category: String) = viewModelScope.launch {
        getMemosByCategoryUseCase(category).collect {
            if (it is Task.Success<List<Memo>>) {
                memoList = it.data
                _memos.postValue(memoList)
            }
        }
    }
}