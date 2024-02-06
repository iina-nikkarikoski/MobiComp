package com.example.myapplication

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking

class UserViewModel(application: Application): AndroidViewModel(application) {
    private val repository: UserRepository
    val allUsers: LiveData<List<User>>

    init {
        val userDao = UserDatabase.getDatabase(application).dao()
        repository = UserRepository(userDao)
        allUsers = repository.allUsers
    }

    fun insert(user: User) {
        viewModelScope.launch(Dispatchers.IO) {
            repository.insert(user)
        }
    }

    suspend fun getLastUser(): User? {
        return repository.getLastUser()
    }

    private val _latestUserName = MutableLiveData<String>()
    val latestUserName: LiveData<String> get() = _latestUserName

    // Function to update the latest user's name
    fun updateLatestUserName() {
        viewModelScope.launch(Dispatchers.IO) {
            // Fetch the latest user from the repository and update the LiveData
            _latestUserName.postValue(repository.getLastUser().toString())
        }
    }

}
/*class UserViewModel(private val dao: UserDao): ViewModel() {

    val state = MutableStateFlow(UserState())

    fun onEvent(event: UserEvent) {
        when(event) {
            is UserEvent.saveUser -> {
                val name = state.value.name
                val picture = state.value.picture

                if (picture != null) {
                    if (name.isBlank() || picture.isBlank()) {
                        return
                    }
                }

                val user = User(name = name, uri = picture)
                viewModelScope.launch { dao.insertUser(user) }
                state.update { it.copy(name = "", picture = "") }
            }
            is UserEvent.SetName -> {
                state.update { it.copy(name = event.name) }
            }
            is UserEvent.SetUri -> {
                state.update { it.copy(picture = event.uri) }
            }
        }
    }
}*/