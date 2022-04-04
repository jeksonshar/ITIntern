package com.example.internitcraft

import androidx.lifecycle.AbstractSavedStateViewModelFactory
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.savedstate.SavedStateRegistryOwner

class MyViewModelFactory(
    private val testDataStore: TestDataStore,
    owner: SavedStateRegistryOwner
    ): AbstractSavedStateViewModelFactory(owner, null) {

//    override fun <T : ViewModel> create(modelClass: Class<T>): T {
//        return modelClass.getConstructor(TestDataStore::class.java).newInstance(testDataStore)
//    }

    override fun <T : ViewModel?> create(
        key: String,
        modelClass: Class<T>,
        handle: SavedStateHandle
    ): T {
        return MainViewModel(testDataStore, handle) as T
    }

}