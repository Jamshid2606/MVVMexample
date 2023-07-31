package com.jama.mvvmexample

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import androidx.lifecycle.ViewModelProvider
import com.jama.mvvmexample.database.AppDatabase
import com.jama.mvvmexample.databinding.ActivityMainBinding
import com.jama.mvvmexample.networking.ApiClient
import com.jama.mvvmexample.utils.NetworkHelper
import com.jama.mvvmexample.vm.Resource
import com.jama.mvvmexample.vm.UserViewModel
import com.jama.mvvmexample.vm.UserViewModelFactory
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch
import kotlin.coroutines.CoroutineContext


// View
class MainActivity : AppCompatActivity(), CoroutineScope {
    private lateinit var binding: ActivityMainBinding
    private lateinit var userViewModel: UserViewModel
    private val TAG = "MainActivity"
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        userViewModel = ViewModelProvider(
            this,
            UserViewModelFactory(AppDatabase.getInstance(this),
            ApiClient.apiService,
            NetworkHelper(this)
            ))[UserViewModel::class.java]
        userViewModel.fetchUsers()
        launch {
            userViewModel.getStateFlow()
                .collect{
                    when(it){
                        is Resource.Succes->{
                            it.data.forEach {data->
                                Log.d(TAG, "onCreate: ${data.login}")
                            }
                        }
                        is Resource.Error->{
                            Log.d(TAG, "onCreate: ${it.error}")
                        }
                        is Resource.Loading->{

                        }
                    }
                }
        }
        // MVVM -> Model View ViewModel

    }

    override val coroutineContext: CoroutineContext
        get() = Dispatchers.Main
}