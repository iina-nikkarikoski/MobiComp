package com.example.homework2

import android.app.Application
import androidx.room.Room

class InitDB : Application() {
    companion object {
        lateinit var database: RoomDatabase
            private set
    }

    override fun onCreate() {
        super.onCreate()

        database = Room.databaseBuilder(
            applicationContext,
            RoomDatabase::class.java,
            "user_database"
        ).build()
    }
}