package com.example.internitcraft

import android.content.Context
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.booleanPreferencesKey
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.stringPreferencesKey
import androidx.datastore.preferences.preferencesDataStore
import com.example.internitcraft.databinding.ActivityMainBinding
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch
import kotlin.coroutines.coroutineContext

class MainActivity : AppCompatActivity() {

    companion object {
        private val Context.dataStore: DataStore<Preferences> by preferencesDataStore(name = "settings")
        val CHECK_BOX_1 = booleanPreferencesKey("check box 1")
        val CHECK_BOX_2 = booleanPreferencesKey("check box 2")
        val CHECK_BOX_1_TEXT = stringPreferencesKey("check box 1 text")
        val CHECK_BOX_2_TEXT = stringPreferencesKey("check box 2 text")
        val FIRST_LAUNCH = booleanPreferencesKey("first launch")
    }

    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        val view = binding.root
        setContentView(view)
//
//        val exampleCounterFlow: Flow<Boolean> = dataStore.data
//            .map { preferences ->
//                // No type safety.
//                preferences[CHECK_BOX_1] ?: false
//            }

        CoroutineScope(Dispatchers.Main).launch {
            if (dataStore.data.first()[FIRST_LAUNCH] == true) {
                binding.textMain.text = "Hello not at first time!!!"
            } else {
                binding.textMain.text = "Hello at first time!!!"
                dataStore.edit {
                    it[FIRST_LAUNCH] = true
                }
            }
            binding.checkBox1Main.isChecked = dataStore.data.first()[CHECK_BOX_1] ?: false
            binding.checkBox1Main.text = dataStore.data.first()[CHECK_BOX_1_TEXT]

            binding.checkBox2Main.isChecked = dataStore.data.first()[CHECK_BOX_2] ?: false
            binding.checkBox2Main.text = dataStore.data.first()[CHECK_BOX_2_TEXT]
        }

        binding.checkBox1Main.setOnClickListener {
            val text1: String
            if (binding.checkBox1Main.isChecked) {
                text1 = "On"
                binding.checkBox1Main.text = text1
            } else {
                text1 = "Off"
                binding.checkBox1Main.text = text1
            }
            CoroutineScope(Dispatchers.Main).launch {
                saveValueBox1(binding.checkBox1Main.isChecked, text1)
                Log.d("TAG", "Сохраненное значение 1: ${dataStore.data.first()[CHECK_BOX_1]}")
            }
        }

        binding.checkBox2Main.setOnClickListener {
            val text2: String
            if (binding.checkBox2Main.isChecked) {
                text2 = "On"
                binding.checkBox2Main.text = text2
            } else {
                text2 = "Off"
                binding.checkBox2Main.text = text2
            }
            CoroutineScope(Dispatchers.Main).launch {
                saveValueBox2(binding.checkBox2Main.isChecked, text2)
                Log.d("TAG", "Сохраненное значение 2: ${dataStore.data.first()[CHECK_BOX_2]}")
            }
        }
    }

    private suspend fun saveValueBox1(value: Boolean, str: String) {
        dataStore.edit {
            it[CHECK_BOX_1] = value
            it[CHECK_BOX_1_TEXT] = str
        }
    }

    private suspend fun saveValueBox2(value: Boolean, str: String) {
        dataStore.edit {
            it[CHECK_BOX_2] = value
            it[CHECK_BOX_2_TEXT] = str
        }
    }
}