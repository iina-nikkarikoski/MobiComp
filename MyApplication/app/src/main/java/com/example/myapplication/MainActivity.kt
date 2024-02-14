package com.example.myapplication

import android.content.pm.PackageManager
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.platform.LocalContext
import androidx.core.content.ContextCompat
import androidx.lifecycle.ViewModel
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.myapplication.ui.theme.MyApplicationTheme
import android.Manifest
import android.os.Build

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




