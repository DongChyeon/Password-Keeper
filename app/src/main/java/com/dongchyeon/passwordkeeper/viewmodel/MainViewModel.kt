package com.dongchyeon.passwordkeeper.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.asLiveData
import com.dongchyeon.passwordkeeper.data.room.MemosRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class MainViewModel @Inject constructor(
    private val repository: MemosRepository
) : ViewModel() {
    val categories: LiveData<List<String>> = repository.getAllCategories().asLiveData()
}