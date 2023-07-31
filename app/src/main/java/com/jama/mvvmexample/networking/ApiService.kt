package com.jama.mvvmexample.networking

import com.jama.mvvmexample.models.UserData
import kotlinx.coroutines.flow.Flow
import retrofit2.http.GET

interface ApiService {
    @GET("users")
    fun getUsers(): Flow<List<UserData>>
}