package com.example.myapplication

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query

@Dao
interface UserDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(user: User)

    @Query("SELECT * FROM users ORDER BY id DESC LIMIT 1")
    suspend fun getLastUser(): User?

    @Query("SELECT * FROM users")
    fun getAllUsers(): LiveData<List<User>>
}

@Dao
interface MessageDao {
    @Insert
    suspend fun insertMessage(messageDB: MessageDB)

    @Query("SELECT * FROM messages ORDER BY id DESC LIMIT 1")
    suspend fun getLastMessage(): MessageDB

    @Query("SELECT * FROM messages")
    fun getAllMessages(): LiveData<List<MessageDB>>
}
