package com.example.myapplication

import android.app.Application
import android.content.Context
import android.net.Uri
import android.util.Log
import androidx.core.net.toUri
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class UserViewModel(application: Application): AndroidViewModel(application) {
    private val repository: UserRepository
    private val messageRepo: MessageRepository
    val allUsers: LiveData<List<User>>
    val allMessages: LiveData<List<MessageDB>>

    init {
        val userDao = UserDatabase.getDatabase(application).dao()
        val messageDao = UserDatabase.getDatabase(application).messageDao()
        repository = UserRepository(userDao)
        messageRepo = MessageRepository(messageDao)
        allUsers = repository.allUsers
        allMessages = messageRepo.allMessages
    }

    fun insert(user: User) {
        viewModelScope.launch(Dispatchers.IO) {
            repository.insert(user)
        }
    }

    fun insertMessage(messageDB: MessageDB) {
        viewModelScope.launch(Dispatchers.IO) {
            messageRepo.insertMessage(messageDB)
        }
    }
}
