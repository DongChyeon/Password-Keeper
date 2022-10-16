package com.dongchyeon.passwordkeeper.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.asLiveData
import androidx.lifecycle.viewModelScope
import com.dongchyeon.passwordkeeper.data.room.Memo
import com.dongchyeon.passwordkeeper.data.room.MemosRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class MemoViewModel @Inject constructor(
    private val repository: MemosRepository
) : ViewModel() {
    private lateinit var _memos: LiveData<List<Memo>>
    val memos: LiveData<List<Memo>> get() = _memos

    fun loadAllMemos() = viewModelScope.launch {
        _memos = repository.getAllMemos().asLiveData()
    }

    fun loadMemosByCategory(category: String) = viewModelScope.launch {
        _memos = repository.getMemosByCategory(category).asLiveData()
    }

    fun insert(memo: Memo) = viewModelScope.launch {
        repository.insertMemo(memo)
    }

    fun update(memo: Memo) = viewModelScope.launch {
        repository.updateMemo(memo)
    }

    fun delete(memo: Memo) = viewModelScope.launch {
        repository.deleteMemo(memo)
    }
}