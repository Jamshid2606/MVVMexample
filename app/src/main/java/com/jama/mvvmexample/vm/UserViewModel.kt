package com.jama.mvvmexample.vm

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.jama.mvvmexample.database.AppDatabase
import com.jama.mvvmexample.database.entity.UserEntity
import com.jama.mvvmexample.mapper.mapToEntity
import com.jama.mvvmexample.networking.ApiService
import com.jama.mvvmexample.repository.UserRepository
import com.jama.mvvmexample.utils.NetworkHelper
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.flatMapConcat
import kotlinx.coroutines.launch

// ViewModel

class UserViewModel(
    private var appDatabase: AppDatabase,
    private var apiService: ApiService,
    private var networkHelper: NetworkHelper
):ViewModel() {
    private var userRepository:UserRepository = UserRepository(apiService, appDatabase.userDao())
    private val stateFlow = MutableStateFlow<Resource<List<UserEntity>>>(Resource.Loading())

    init {
        fetchUsers()
    }

    fun fetchUsers() {
        viewModelScope.launch {
            if (networkHelper.isNetworkConnecting()) {
                userRepository.getUsers()
                    .catch {
                        stateFlow.emit(Resource.Error(it))
                    }
                    .flatMapConcat {
                        val list = ArrayList<UserEntity>()
                        it.forEach {
                            list.add(it.mapToEntity(it))
                        }
                        userRepository.addUsers(list)
                    }
                    .collect {
                        stateFlow.emit(Resource.Succes(userRepository.getDbUsers()))
                    }
            }else{
                if (userRepository.getUserCount()>0){
                    stateFlow.emit(Resource.Succes(userRepository.getDbUsers()))
                }else{
                    stateFlow.emit(Resource.Error(Throwable("Internet not connection")))
                }
            }
        }
    }
    fun getStateFlow():StateFlow<Resource<List<UserEntity>>>{
        return stateFlow
    }


}