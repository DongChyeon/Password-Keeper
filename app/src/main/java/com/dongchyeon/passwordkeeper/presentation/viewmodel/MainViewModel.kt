package com.dongchyeon.passwordkeeper.presentation.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.dongchyeon.passwordkeeper.data.Task
import com.dongchyeon.passwordkeeper.domain.GetCategoriesUseCase
import com.dongchyeon.passwordkeeper.presentation.base.BaseViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class MainViewModel @Inject constructor(
    private val getCategoriesUseCase: GetCategoriesUseCase
) : BaseViewModel() {
    private lateinit var categoryList: List<String>
    private val _categories = MutableLiveData<List<String>>()
    val categories: LiveData<List<String>> get() = _categories
    val message1 = "스와이프해서 선택하세요"
    val message2 = "새 항목을 추가해 주세요"

    fun loadAllCategories() = viewModelScope.launch {
        getCategoriesUseCase().collect {
            if (it is Task.Success<List<String>>) {
                categoryList = it.data
            }
            _categories.postValue(categoryList)
        }
    }
}