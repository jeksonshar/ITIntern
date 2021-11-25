package com.example.internitcraft

import androidx.lifecycle.*
import kotlinx.coroutines.launch

class MainViewModel(private val testDataStore: TestDataStore) : ViewModel() {

    val firstLaunch: LiveData<Boolean> = testDataStore.getFirstLaunch().asLiveData()
    val checkBox1State: LiveData<Boolean> = testDataStore.getFirstCheckboxState().asLiveData()
    val checkBox2State: LiveData<Boolean> = testDataStore.getSecondCheckboxState().asLiveData()

    init {
        viewModelScope.launch {
            testDataStore.getFirstLaunch()
        }
    }

    fun onSetTextCheckbox1() = viewModelScope.launch {
        if (checkBox1State.value == true) {
            testDataStore.editFirstCheckboxState(false)
        } else {
            testDataStore.editFirstCheckboxState(true)
        }
    }

    fun onSetTextCheckbox2() = viewModelScope.launch {
        if (checkBox2State.value == true) {
             testDataStore.editSecondCheckboxState(false)
        } else {
             testDataStore.editSecondCheckboxState(true)
        }
    }

    fun onSigned() = viewModelScope.launch {
        testDataStore.editFirstLaunch(false)
    }

    fun onClear() = viewModelScope.launch {
        testDataStore.editFirstLaunch(true)
    }

}