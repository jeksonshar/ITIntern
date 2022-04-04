package com.example.internitcraft

import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.util.Log
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import com.example.internitcraft.databinding.ActivityMainBinding
import com.google.firebase.database.ktx.database
import com.google.firebase.ktx.Firebase
import kotlinx.coroutines.*
import java.lang.Runnable

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding

//    private val mainViewModel: MainViewModel by lazy {
//        val myFactory = MyViewModelFactory(TestDataStore(this))
//        ViewModelProvider(this, myFactory).get(MainViewModel::class.java)
//    }

    lateinit var scope: Job     // для тестирования корутин
    val job1 = Job()
    val job2 = Job()
    val job3 = Job()

    private val mainViewModel: MainViewModel by viewModels {
        MyViewModelFactory(TestDataStore(this), this)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = DataBindingUtil.setContentView(this, R.layout.activity_main)
        binding.lifecycleOwner = this
        binding.mainViewModel = mainViewModel

        mainViewModel.firstLaunch.observe(this) {
            Log.d("TAG", "firstLaunch value = $it")
        }

        Log.d("TAG", "-${binding.editText.text}-")
        mainViewModel.bundleData.observe(this) {
            binding.setSavedStateHandle.text = it
        }

        // обработчик исключений в корутинах
        val handler = CoroutineExceptionHandler {
            context, exception ->
            Log.d("TAGG", "caught $exception in [${context}] ")
        }

        // тестируем корутины
        CoroutineScope(job1 + Dispatchers.Main  + handler  + CoroutineName("job1")).launch {
            CoroutineScope(job2 + Dispatchers.IO + handler + CoroutineName("job2")).launch {
                delay(1000)
                Log.d("TAG", "job2: ")
                throw Exception("Failed coroutine")
            }
            CoroutineScope(job3 + handler  + CoroutineName("job3")).launch {
                delay(2000)
                Log.d("TAG", "job3: ")
                throw Exception("Failed another")
            }
            delay(3000)
            Log.d("TAG", "job1: ")
            throw Exception("Something else")
        }
    }

    override fun onStart() {
        super.onStart()
            val fireBase = Firebase.database
            val myRef = fireBase.getReference("Oleg")
            myRef.child("Oleg's car").child("home").setValue("Nataha")
    }

    override fun onResume() {
        super.onResume()
        job1.cancel()   // отменяем выполнение корутины
    }

    override fun finish() {
        Handler(Looper.getMainLooper()).postDelayed(Runnable { mainViewModel.onSigned() }, 300)
        Log.d("TAG", "onCreate: ${binding.editText.text}")
        super.finish()
    }
}