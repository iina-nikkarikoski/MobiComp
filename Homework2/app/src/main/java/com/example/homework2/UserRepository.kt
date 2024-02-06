package com.example.homework2

import android.content.Context
import androidx.lifecycle.LiveData
import com.example.homework2.UserDao

class UserRepository (private val userDao: UserDao) {
    suspend fun insert(user: User) {
        userDao.insert(user)
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