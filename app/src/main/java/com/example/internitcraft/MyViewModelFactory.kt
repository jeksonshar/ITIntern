package com.example.internitcraft

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider

class MyViewModelFactory(private val testDataStore: TestDataStore): ViewModelProvider.Factory {

    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        return modelClass.getConstructor(TestDataStore::class.java).newInstance(testDataStore)
    }

}