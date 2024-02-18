package com.example.myapplication

import androidx.lifecycle.LiveData

class UserRepository (private val userDao: UserDao) {
    val allUsers: LiveData<List<User>> = userDao.getAllUsers()

    suspend fun insert(user: User) {
        userDao.insert(user)
    }
}

class MessageRepository (private val messageDao: MessageDao) {
    val allMessages: LiveData<List<MessageDB>> = messageDao.getAllMessages()

    suspend fun insertMessage(messageDB: MessageDB) {
        messageDao.insertMessage(messageDB)
    }
}