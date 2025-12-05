package com.hitit.app.service

import android.content.Context
import android.hardware.Sensor
import android.hardware.SensorEvent
import android.hardware.SensorEventListener
import android.hardware.SensorManager
import kotlinx.coroutines.channels.awaitClose
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.callbackFlow
import kotlinx.coroutines.flow.distinctUntilChanged

actual class DeviceOrientationService(private val context: Context) {

    private val sensorManager: SensorManager by lazy {
        context.getSystemService(Context.SENSOR_SERVICE) as SensorManager
    }

    private val accelerometer: Sensor? by lazy {
        sensorManager.getDefaultSensor(Sensor.TYPE_ACCELEROMETER)
    }

    private var listener: SensorEventListener? = null
    private val _orientation = MutableStateFlow(DeviceOrientation.OTHER)

    actual fun observeOrientation(): Flow<DeviceOrientation> = callbackFlow {
        val sensorListener = object : SensorEventListener {
            override fun onSensorChanged(event: SensorEvent) {
                if (event.sensor.type == Sensor.TYPE_ACCELEROMETER) {
                    val z = event.values[2]

                    // Z-axis: positive when face-up, negative when face-down
                    // Threshold of ~7 m/s² (gravity is ~9.8 m/s²)
                    val orientation = when {
                        z < -FACE_DOWN_THRESHOLD -> DeviceOrientation.FACE_DOWN
                        z > FACE_UP_THRESHOLD -> DeviceOrientation.FACE_UP
                        else -> DeviceOrientation.OTHER
                    }

                    trySend(orientation)
                }
            }

            override fun onAccuracyChanged(sensor: Sensor?, accuracy: Int) {
                // Not needed for our use case
            }
        }

        accelerometer?.let { sensor ->
            sensorManager.registerListener(
                sensorListener,
                sensor,
                SensorManager.SENSOR_DELAY_UI
            )
        }

        awaitClose {
            sensorManager.unregisterListener(sensorListener)
        }
    }.distinctUntilChanged()

    actual fun start() {
        listener = object : SensorEventListener {
            override fun onSensorChanged(event: SensorEvent) {
                if (event.sensor.type == Sensor.TYPE_ACCELEROMETER) {
                    val z = event.values[2]
                    val orientation = when {
                        z < -FACE_DOWN_THRESHOLD -> DeviceOrientation.FACE_DOWN
                        z > FACE_UP_THRESHOLD -> DeviceOrientation.FACE_UP
                        else -> DeviceOrientation.OTHER
                    }
                    _orientation.value = orientation
                }
            }

            override fun onAccuracyChanged(sensor: Sensor?, accuracy: Int) {}
        }

        accelerometer?.let { sensor ->
            sensorManager.registerListener(
                listener,
                sensor,
                SensorManager.SENSOR_DELAY_UI
            )
        }
    }

    actual fun stop() {
        listener?.let { sensorManager.unregisterListener(it) }
        listener = null
    }

    companion object {
        // Threshold for detecting face-down/face-up (m/s²)
        // Gravity is approximately 9.8 m/s², so ~7 gives us some margin
        private const val FACE_DOWN_THRESHOLD = 7.0f
        private const val FACE_UP_THRESHOLD = 7.0f
    }
}
