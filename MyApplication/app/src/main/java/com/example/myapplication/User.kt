package com.example.myapplication

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "users")
data class User(
    @PrimaryKey(autoGenerate = true)
    val id: Long = 0,
    @ColumnInfo(name  = "username")
    val name: String,
    @ColumnInfo(name  = "pictureUri")
    val picture: String?
)

@Entity(tableName = "messages")
data class MessageDB(
    @PrimaryKey(autoGenerate = true)
    val id: Long = 0,
    @ColumnInfo(name = "username")
    val name: String,
    @ColumnInfo(name = "message")
    val message: String
)