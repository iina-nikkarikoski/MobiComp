package com.example.homework2

import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable

@Composable
fun Navigation(navController: NavHostController, db: AppDatabase) {

    NavHost(navController = navController, startDestination = Screen.MessageScreen.route) {
        composable(route = Screen.MessageScreen.route) {
            Conversation(navController = navController, SampleData.conversationSample)
        }
        composable(route = Screen.SecondScreen.route) {
            val userRepository = UserRepository(db.userDao())
            SecondScreen(navController = navController, db = db, userRepository = userRepository)
        }
    }
}