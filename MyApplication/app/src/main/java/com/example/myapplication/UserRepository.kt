package com.example.myapplication

import androidx.lifecycle.LiveData

class UserRepository (private val userDao: UserDao) {
    val allUsers: LiveData<List<User>> = userDao.getAllUsers()

    suspend fun insert(user: User) {
        userDao.insert(user)
    }
}