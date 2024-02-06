package com.example.homework2

import android.app.Application
import android.content.Context
import android.util.Log
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.room.Room
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import java.lang.Exception

class UserViewModel(application: Application): AndroidViewModel(application) {
    private val repository: UserRepository

    init {
        val userDao = RoomDatabase.getDatabase(application).userDao()
        repository = UserRepository(userDao)
    }

    fun insert(user: User) {
        viewModelScope.launch(Dispatchers.IO) {
            repository.insert(user)
        }
    }
}

/*class UserViewModel(application: Application) : AndroidViewModel(application) {
    private val db : RoomDatabase by lazy {
        Room.databaseBuilder(application, RoomDatabase::class.java, "user_db")
            .fallbackToDestructiveMigration()
            .build()
    }

    val userProfile = MutableLiveData<User?>()

    init {
        loadUserProfile()
    }

    fun saveUser(username: String, imageUri: String?) {
        viewModelScope.launch (Dispatchers.IO) {
            try {
                val newUser = User(name = username, picture = imageUri ?: "")
                db.userDao().insert(newUser)
                userProfile.postValue(newUser)
            } catch (e: Exception) {
                Log.e("UserViewModel", "Error saving user profile", e)
            }
        }
    }

    private fun loadUserProfile() {
        viewModelScope.launch {
            userProfile.postValue(db.userDao().getAllUsers())
        }
    }*/

/*class UserViewModel (context: Context) : ViewModel() {
    private val userRepository = UserRepository(context)
    fun addUser(user: User) {
        viewModelScope.launch {
            userRepository.insert(user)
        }
    }

    fun getAllUsers() = userRepository.getAllUsers()
}*/

/*class UserViewModel(application: Application): AndroidViewModel(application) {
    private val repository: UserRepository
    private val readAll : LiveData<List<User>>
    init {
        //val userDB = RoomDatabase.getDatabase(application).userDao()
        //repository = UserRepository(userDB)
        val userDB : androidx.room.RoomDatabase =  RoomDatabase.getDatabase(application)
        val dao = UserRepository(userDB.userDao())
        repository = dao
        readAll = repository.getAllUsers()
    }

    fun addUser(user: User) {
        viewModelScope.launch (Dispatchers.IO) {
            repository.insert(user)
        }
    }
}*/
