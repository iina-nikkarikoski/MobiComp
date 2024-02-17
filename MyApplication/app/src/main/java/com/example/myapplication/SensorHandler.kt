package com.example.myapplication

import android.hardware.Sensor
import android.hardware.SensorEvent
import android.hardware.SensorEventListener
import android.hardware.SensorManager

class SensorHandler(private val sensorManager: SensorManager) {

    private val gyroscopeListener = object : SensorEventListener {
        override fun onSensorChanged(event: SensorEvent) {
        }
        override fun onAccuracyChanged(sensor: Sensor?, accuracy: Int) {
        }
    }

    fun startGyroscopeListening() {
        val gyroscopeSensor = sensorManager.getDefaultSensor(Sensor.TYPE_GYROSCOPE)

        if (gyroscopeSensor != null) {
            sensorManager.registerListener(gyroscopeListener, gyroscopeSensor, SensorManager.SENSOR_DELAY_NORMAL)
        }
    }

    fun stopGyroscopeListening() {
        sensorManager.unregisterListener(gyroscopeListener)
    }
}