package com.example.homework2

import android.content.Context
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.room.Room
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

/*class UserViewModel(private val userDao: UserDao) : ViewModel() {
    fun saveUser(text: String, uri: String?) {
        viewModelScope.launch {
            val user = User(name = text, picture = uri)
            userDao.insert(user)
        }
    }
    private val userRepository: UserRepository) : ViewModel()
}*/

class UserViewModel(private val userRepository: UserRepository) : ViewModel() {
    fun saveUser(name: String, imageUri: String) {
        viewModelScope.launch(Dispatchers.IO) {
            userRepository.insert(User(name = name, picture = imageUri))
        }
    }
}