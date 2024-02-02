package com.example.homework2

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Update
import kotlinx.coroutines.flow.Flow

@Dao
interface UserDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(user: User)
    @Update
    suspend fun update(user: User)
    @Query("SELECT * FROM users")
    fun getAllNotes(): Flow<List<User>>
}