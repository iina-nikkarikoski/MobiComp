package com.example.myapplication

data class UserState (
    val users: List<User> = emptyList(),
    val name: String = "",
    val picture: String? = ""
)