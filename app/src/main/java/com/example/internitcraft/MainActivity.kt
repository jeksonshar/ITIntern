package com.example.internitcraft

import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.ViewModelProvider
import com.example.internitcraft.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding
    private val mainViewModel: MainViewModel by lazy {
        val myFactory = MyViewModelFactory(TestDataStore(this))
        ViewModelProvider(this, myFactory).get(MainViewModel::class.java)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = DataBindingUtil.setContentView(this, R.layout.activity_main)
        binding.lifecycleOwner = this
        binding.mainViewModel = mainViewModel

        mainViewModel.firstLaunch.observe(this) {
            Log.d("TAG", "firstLaunch value = $it")
        }
    }

    override fun onStop() {
        super.onStop()
        mainViewModel.onSigned()
    }
}