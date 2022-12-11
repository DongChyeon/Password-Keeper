package com.dongchyeon.passwordkeeper.presentation.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.asLiveData
import androidx.lifecycle.viewModelScope
import com.dongchyeon.passwordkeeper.data.model.Memo
import com.dongchyeon.passwordkeeper.domain.*
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class MemoViewModel @Inject constructor(
    private val getMemosUseCase: GetMemosUseCase,
    private val getMemosByCategoryUseCase: GetMemosByCategoryUseCase,
    private val insertMemoUseCase: InsertMemoUseCase,
    private val updateMemoUseCase: UpdateMemoUseCase,
    private val deleteMemoUseCase: DeleteMemoUseCase
) : ViewModel() {
    private lateinit var _memos: LiveData<List<Memo>>
    val memos: LiveData<List<Memo>> get() = _memos

    fun loadAllMemos() = viewModelScope.launch {
        _memos = getMemosUseCase().asLiveData()
    }

    fun loadMemosByCategory(category: String) = viewModelScope.launch {
        _memos = getMemosByCategoryUseCase(category).asLiveData()
    }

    fun insert(memo: Memo) = viewModelScope.launch {
        insertMemoUseCase(memo)
    }

    fun update(memo: Memo) = viewModelScope.launch {
        updateMemoUseCase(memo)
    }

    fun delete(memo: Memo) = viewModelScope.launch {
        deleteMemoUseCase(memo)
    }
}