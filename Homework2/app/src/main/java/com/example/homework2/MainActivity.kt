package com.example.homework2

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import com.example.homework2.ui.theme.Homework2Theme
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import androidx.room.Room


class MainActivity : ComponentActivity() {

    lateinit var navController: NavHostController
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            Homework2Theme {
                navController = rememberNavController()
                val db = Room.databaseBuilder(
                    applicationContext,
                    AppDatabase::class.java, "users_database"
                ).build()

                //by lazy { room....

                Navigation(navController = navController, db)

            }
        }
    }
}
