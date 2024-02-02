package com.example.homework2

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.launch

class AppViewModel(private val userRepository: UserRepository) : ViewModel() {

    //val allUsers: LiveData<List<User>> = userRepository.allUsers.asLiveData()
    fun insert(user: User) = viewModelScope.launch {
        userRepository.insert(user)
    }
}

/*class AppViewModelFactory(private val userRepository: UserRepository) : ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(AppViewModel::class.java)) {
            @Suppress("UNCHECKED_CAST")
            return AppViewModel(userRepository) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}*/
