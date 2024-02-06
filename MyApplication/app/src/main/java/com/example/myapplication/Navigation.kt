package com.example.myapplication

import androidx.compose.runtime.Composable
import androidx.lifecycle.viewModelScope
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

/*Composable
fun Navigation(navController: NavHostController) {

    NavHost(navController = navController, startDestination = Screen.MessageScreen.route) {
        composable(route = Screen.MessageScreen.route) {
            Conversation(navController = navController, SampleData.conversationSample)
        }
        composable(route = Screen.SecondScreen.route) {
            SecondScreen(navController = navController)
        }
    }
}*/