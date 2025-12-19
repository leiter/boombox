package com.hitit.app.service

import kotlinx.coroutines.flow.Flow

/**
 * Represents the device orientation based on accelerometer data
 */
enum class DeviceOrientation {
    /** Device is face-up (screen facing up) */
    FACE_UP,
    /** Device is face-down (screen facing down) */
    FACE_DOWN,
    /** Device is in any other orientation */
    OTHER
}

/**
 * Platform-specific service for detecting device orientation using accelerometer
 */
@Suppress("EXPECT_ACTUAL_CLASSIFIERS_ARE_IN_BETA_WARNING")
expect class DeviceOrientationService {
    /**
     * Observe device orientation changes as a Flow
     * @return Flow emitting DeviceOrientation values when orientation changes
     */
    fun observeOrientation(): Flow<DeviceOrientation>

    /**
     * Start listening for orientation changes
     */
    fun start()

    /**
     * Stop listening for orientation changes to save battery
     */
    fun stop()
}
