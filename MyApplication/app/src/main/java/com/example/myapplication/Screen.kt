package com.example.myapplication

sealed class Screen(val route: String) {
    object MessageScreen : Screen(route = "message_screen")
    object SecondScreen : Screen(route = "second_screen")
    object Camera : Screen(route = "Camera")
}