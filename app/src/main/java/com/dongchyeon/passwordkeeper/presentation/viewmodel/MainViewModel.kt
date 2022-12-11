package com.dongchyeon.passwordkeeper.presentation.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.asLiveData
import com.dongchyeon.passwordkeeper.domain.GetCategoriesUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class MainViewModel @Inject constructor(
    private val getCategoriesUseCase: GetCategoriesUseCase
) : ViewModel() {
    val categories: LiveData<List<String>> = getCategoriesUseCase().asLiveData()
    val message1 = "스와이프해서 선택하세요"
    val message2 = "새 항목을 추가해 주세요"
}