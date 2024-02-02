package com.example.homework2

class UserRepository(private val userDao: UserDao?) {

    //val allUsers: Flow<List<User>>? = userDao?.getAllNotes()

    suspend fun insert(user: User) {
        userDao?.insert(user)
    }
}