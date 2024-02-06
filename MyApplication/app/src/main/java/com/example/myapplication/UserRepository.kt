package com.example.myapplication

import androidx.lifecycle.LiveData

class UserRepository (private val userDao: UserDao) {
    val allUsers: LiveData<List<User>> = userDao.getAllUsers()

    suspend fun insert(user: User) {
        userDao.insert(user)
    }
    suspend fun getLastUser(): User? {
        return userDao.getLastUser()
    }
}
/*class UserRepository(context: Context) {
    private val userDao: UserDao = RoomDatabase.getDatabase(context).userDao()
    suspend fun insert(user: User) {
        userDao.insert(user)
    }
    suspend fun update(user: User) {
        userDao.update(user)
    }
    fun getAllUsers(): LiveData<List<User>> = userDao.getAllUsers()

}*/