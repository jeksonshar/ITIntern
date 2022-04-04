package com.example.internitcraft

import androidx.lifecycle.*
import kotlinx.coroutines.launch

class MainViewModel(
    private val testDataStore: TestDataStore,
    private val savedStateHandle: SavedStateHandle
) : ViewModel() {

    val firstLaunch: LiveData<Boolean> = testDataStore.getFirstLaunch().asLiveData()
    val checkBox1State: LiveData<Boolean> = testDataStore.getFirstCheckboxState().asLiveData()
    val checkBox2State: LiveData<Boolean> = testDataStore.getSecondCheckboxState().asLiveData()

    //    val textObserv = MutableLiveData<String>()
    val editTextHandle = MutableLiveData<String>()
    val bundleData = MutableLiveData<String>()


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
//        textObserv.value = ("Значение: ${Random.nextInt(10, 100)}")
        bundleData.value =
            savedStateHandle.getLiveData<String>(HANDLE_EDIT_TEXT).value ?: "No value"
    }

    fun setEditTextHandle(text: String) {
        savedStateHandle.set(HANDLE_EDIT_TEXT, text)
    }

    companion object {
        const val HANDLE_EDIT_TEXT = "handle edit text"
    }

}