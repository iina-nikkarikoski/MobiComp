package com.example.myapplication

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import androidx.lifecycle.ViewModel
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.myapplication.ui.theme.MyApplicationTheme

class MainActivity : ComponentActivity() {
    private lateinit var navController: NavHostController

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            MyApplicationTheme {
                navController = rememberNavController()
                val viewModel: UserViewModel by viewModels {
                    ViewModelFactory(application)
                }

                NavHost(navController = navController, startDestination = Screen.MessageScreen.route) {
                    composable(route = Screen.MessageScreen.route) {
                        Conversation(navController = navController, SampleData.conversationSample, viewModel = viewModel)
                    }
                    composable(route = Screen.SecondScreen.route) {
                        SecondScreen(navController = navController, viewModel = viewModel)
                    }
                }
            }
        }
    }
}




