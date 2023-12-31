package com.jama.mvvmexample.database

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.jama.mvvmexample.database.dao.UserDao
import com.jama.mvvmexample.database.entity.UserEntity

@Database(entities = [UserEntity::class], version = 1)
abstract class AppDatabase : RoomDatabase() {
    abstract fun userDao() : UserDao
    companion object {
        private var INSTANCE : AppDatabase?=null
        @Synchronized
        fun getInstance(context: Context):AppDatabase{
            if (INSTANCE == null){
                INSTANCE = Room.databaseBuilder(context, AppDatabase::class.java, "my_db")
                    .allowMainThreadQueries()
                    .build()
            }
            return INSTANCE!!
        }
    }

}
