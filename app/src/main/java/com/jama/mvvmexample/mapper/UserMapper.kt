package com.jama.mvvmexample.mapper

import com.jama.mvvmexample.database.entity.UserEntity
import com.jama.mvvmexample.models.UserData

fun UserData.mapToEntity(userData: UserData):UserEntity{
    return UserEntity(id = userData.id, login = userData.login, avatar = userData.avatar_url)
}