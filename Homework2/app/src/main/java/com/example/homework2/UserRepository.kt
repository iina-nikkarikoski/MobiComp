package com.example.homework2

import androidx.lifecycle.LiveData

class UserRepository(private val userDao: UserDao) {
    suspend fun insert(user: User) {
        userDao.insert(user)
    }
    suspend fun update(user: User) {
        userDao.update(user)
    }
    fun getAllUsers(): LiveData<List<User>> = userDao.getAllUsers()
}