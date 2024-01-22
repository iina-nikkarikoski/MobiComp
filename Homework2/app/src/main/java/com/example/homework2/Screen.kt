package com.example.homework2

sealed class Screen(val route: String) {
    object MessageScreen : Screen(route = "message_screen")
    object SecondScreen : Screen(route = "second_screen")
}