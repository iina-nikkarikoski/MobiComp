package com.example.homework2

class UserRepository(private val userDao: UserDao?) {
    suspend fun insert(user: User) {
        userDao?.insert(user)
    }
}