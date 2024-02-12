package com.example.myapplication

import android.app.Application
import android.content.Context
import android.net.Uri
import androidx.core.net.toUri
import androidx.datastore.core.DataStore
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking
import java.util.prefs.Preferences

class UserViewModel(application: Application): AndroidViewModel(application) {
    private val repository: UserRepository
    val allUsers: LiveData<List<User>>

    private val sharedPreferences =
        application.getSharedPreferences("user_preferences", Context.MODE_PRIVATE)
    private val imageUriKey = "imageUri"
    private val _profilePic = MutableLiveData<Uri?>()

    init {
        val userDao = UserDatabase.getDatabase(application).dao()
        repository = UserRepository(userDao)
        allUsers = repository.allUsers

        val uriString = sharedPreferences.getString(imageUriKey, null)
        _profilePic.value = uriString?.toUri()
    }

    fun saveImageUri(uri: Uri?) {
        // Save the image URI to SharedPreferences
        sharedPreferences.edit().putString(imageUriKey, uri?.toString()).apply()
        _profilePic.value = uri
    }

    fun insert(user: User) {
        viewModelScope.launch(Dispatchers.IO) {
            repository.insert(user)
        }
    }
}