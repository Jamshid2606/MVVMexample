package com.jama.mvvmexample.database.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.jama.mvvmexample.database.entity.UserEntity
import kotlinx.coroutines.flow.Flow

@Dao
interface UserDao {
    @Insert
    fun addUser(userEntity: UserEntity):Long

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun addUsers(list: List<UserEntity>)

    @Query("select * from userentity")
    fun getUsers(): List<UserEntity>

    @Query("select count(*) from userentity")
    fun getUserCount():Int
}