package com.hitit.app.service

import kotlinx.cinterop.ExperimentalForeignApi
import kotlinx.cinterop.useContents
import kotlinx.coroutines.channels.awaitClose
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.callbackFlow
import kotlinx.coroutines.flow.distinctUntilChanged
import platform.CoreMotion.CMMotionManager
import platform.Foundation.NSOperationQueue

@OptIn(ExperimentalForeignApi::class)
actual class DeviceOrientationService {

    private val motionManager = CMMotionManager()

    actual fun observeOrientation(): Flow<DeviceOrientation> = callbackFlow {
        if (motionManager.accelerometerAvailable) {
            motionManager.accelerometerUpdateInterval = 0.1 // 100ms interval

            motionManager.startAccelerometerUpdatesToQueue(
                NSOperationQueue.mainQueue
            ) { data, _ ->
                data?.let { accelerometerData ->
                    val z = accelerometerData.acceleration.useContents { z }

                    // iOS CoreMotion Z-axis:
                    // - Face UP (screen visible): z ≈ -1.0 (gravity toward screen)
                    // - Face DOWN (screen on table): z ≈ +1.0 (gravity away from screen)
                    val orientation = when {
                        z < -FACE_UP_THRESHOLD -> DeviceOrientation.FACE_UP
                        z > FACE_DOWN_THRESHOLD -> DeviceOrientation.FACE_DOWN
                        else -> DeviceOrientation.OTHER
                    }

                    trySend(orientation)
                }
            }
        }

        awaitClose {
            motionManager.stopAccelerometerUpdates()
        }
    }.distinctUntilChanged()

    actual fun start() {
        if (motionManager.accelerometerAvailable) {
            motionManager.accelerometerUpdateInterval = 0.1
            motionManager.startAccelerometerUpdatesToQueue(
                NSOperationQueue.mainQueue
            ) { _, _ -> }
        }
    }

    actual fun stop() {
        motionManager.stopAccelerometerUpdates()
    }

    companion object {
        // iOS CoreMotion normalizes gravity to 1.0
        private const val FACE_DOWN_THRESHOLD = 0.7
        private const val FACE_UP_THRESHOLD = 0.7
    }
}
