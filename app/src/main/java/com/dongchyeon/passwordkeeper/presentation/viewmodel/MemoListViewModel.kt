package com.dongchyeon.passwordkeeper.presentation.viewmodel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.asLiveData
import androidx.lifecycle.switchMap
import androidx.lifecycle.viewModelScope
import androidx.paging.cachedIn
import com.dongchyeon.passwordkeeper.domain.GetMemosByCategoryUseCase
import com.dongchyeon.passwordkeeper.presentation.base.BaseViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class MemoListViewModel @Inject constructor(
    private val getMemosByCategoryUseCase: GetMemosByCategoryUseCase
) : BaseViewModel() {
    private val category: MutableLiveData<String> = MutableLiveData()

    val memos = category.switchMap { category ->
        getMemosByCategoryUseCase(category).cachedIn(viewModelScope).asLiveData()
    }

    fun setCategory(category: String) {
        this.category.value = category
    }
}