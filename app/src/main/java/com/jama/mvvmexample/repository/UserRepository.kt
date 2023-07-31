package com.jama.mvvmexample.repository

import com.jama.mvvmexample.database.dao.UserDao
import com.jama.mvvmexample.database.entity.UserEntity
import com.jama.mvvmexample.networking.ApiService
import kotlinx.coroutines.flow.flow

//Model
class UserRepository(private var apiService: ApiService, private var userDao: UserDao) {
    fun getUsers() = apiService.getUsers()
    fun addUsers(list: List<UserEntity>) = flow{ emit(userDao.addUsers(list))}
    fun getDbUsers() = userDao.getUsers()
    fun getUserCount() = userDao.getUserCount()
}