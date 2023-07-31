package com.jama.mvvmexample.vm

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.jama.mvvmexample.database.AppDatabase
import com.jama.mvvmexample.networking.ApiService
import com.jama.mvvmexample.utils.NetworkHelper

class UserViewModelFactory(var appDatabase: AppDatabase, var apiService: ApiService,var networkHelper: NetworkHelper) :ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(UserViewModel::class.java)){
            return UserViewModel(appDatabase, apiService, networkHelper) as T
        }
        return throw  Exception("ERRORR ")
    }
}