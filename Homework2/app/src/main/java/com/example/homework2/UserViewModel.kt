package com.example.homework2

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class UserViewModel(application: Application): AndroidViewModel(application) {
    private val repository: UserRepository
    private val readAll : LiveData<List<User>>
    init {
        val userDB = RoomDatabase.getDatabase(application).DAO()
        repository = UserRepository(userDB)
        readAll = repository.getAllUsers()
    }

    fun addUser(user: User) {
        viewModelScope.launch (Dispatchers.IO) {
            repository.insert(user)
        }
    }
}
