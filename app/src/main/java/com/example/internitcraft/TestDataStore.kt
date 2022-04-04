package com.example.internitcraft

import android.content.Context
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.booleanPreferencesKey
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.preferencesDataStore
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

class TestDataStore(context: Context) {

    private val settingsDataStore = context.dataStore

    companion object {
        private val Context.dataStore: DataStore<Preferences> by preferencesDataStore(name = "settings")
        private val FIRST_LAUNCH = booleanPreferencesKey("first launch")
        private val FIRST_CHECKBOX_STATE = booleanPreferencesKey("first checkbox state")
        private val SECOND_CHECKBOX_STATE = booleanPreferencesKey("second checkbox state")
    }

    fun getFirstLaunch(): Flow<Boolean> = settingsDataStore.data.map {
        it[FIRST_LAUNCH] ?: true
    }

    fun getFirstCheckboxState(): Flow<Boolean> = settingsDataStore.data.map {
        it[FIRST_CHECKBOX_STATE] ?: false
    }

    fun getSecondCheckboxState(): Flow<Boolean> = settingsDataStore.data.map {
        it[SECOND_CHECKBOX_STATE] ?: false
    }

    suspend fun editFirstCheckboxState(value: Boolean) {
        settingsDataStore.edit {
            it[FIRST_CHECKBOX_STATE] = value
        }
    }

    suspend fun editSecondCheckboxState(value: Boolean) {
        settingsDataStore.edit {
            it[SECOND_CHECKBOX_STATE] = value
        }
    }

    suspend fun editFirstLaunch(value: Boolean) {
        settingsDataStore.edit {
            it[FIRST_LAUNCH] = value
        }
    }


}