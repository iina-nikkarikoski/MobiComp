package com.example.homework2

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.launch

/*class UserViewModel(private val userDao: UserDao) : ViewModel() {
    fun saveUser(text: String, uri: String?) {
        viewModelScope.launch {
            val user = User(name = text, picture = uri)
            userDao.insert(user)
        }
    }
}*/

class UserViewModel(private val userRepository: UserRepository) : ViewModel() {
    fun saveUser(name: String, imageUri: String) {
        viewModelScope.launch {
            userRepository.insert(User(name = name, picture = imageUri))
        }
    }
}