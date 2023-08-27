package com.example.roomdatabaseapplication.repository

import androidx.lifecycle.LiveData
import com.example.roomdatabaseapplication.dao.UserDao
import com.example.roomdatabaseapplication.data.model.User

class UserRepository (private val userDao: UserDao){

    val readAllData: LiveData<List<User>> = userDao.readAllData()

    suspend fun addUser(user: User){
        userDao.addUser(user)
    }

    suspend fun updateUser(user: User){
        userDao.updateUser(user)
    }

    suspend fun deleteUser(user: User){
        userDao.deleteUser(user)
    }

    suspend fun deleteAllUsers(){
        userDao.deleteAllUsers()
    }

    fun searchUser(searchQuery: String):LiveData<List<User>>{
        return userDao.searchDatabase(searchQuery)
    }

}