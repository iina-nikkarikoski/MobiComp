package com.example.myapplication

import android.content.Context
import android.hardware.SensorManager
import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity

class SensorActivity : AppCompatActivity() {

    private lateinit var SensorHandler: SensorHandler

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(androidx.appcompat.R.layout.abc_alert_dialog_material)
        val sensorManager = getSystemService(Context.SENSOR_SERVICE) as SensorManager
        SensorHandler = SensorHandler(sensorManager)

        SensorHandler.startGyroscopeListening()
    }

    override fun onDestroy() {
        super.onDestroy()
        SensorHandler.stopGyroscopeListening()
    }
}