package com.example.homework2

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.homework2.ui.theme.Homework2Theme
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController


class MainActivity : ComponentActivity() {

    lateinit var navController: NavHostController

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContent {
            Homework2Theme {
                navController = rememberNavController()
                val db = AppDatabase.getInstance(applicationContext)
                val userRepository = UserRepository(db.userDao())
                val appViewModelFactory = AppViewModelFactory(userRepository)

                val userViewModel: UserViewModel = viewModel(factory = appViewModelFactory)

                Navigation(navController = navController, db, userViewModel)

                /*AppDatabase.getInstance(context)

                val db = viewModel.db
                val userRepository = viewModel.userRepository

                Navigation(navController = navController, db, userRepository)*/

            }
        }
    }
}

class AppViewModelFactory(private val userRepository: UserRepository) : ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(AppViewModel::class.java)) {
            @Suppress("UNCHECKED_CAST")
            return AppViewModel(userRepository) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}
