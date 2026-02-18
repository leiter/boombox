package com.hitit.app.di

import com.hitit.app.service.AppLauncher
import com.hitit.app.service.AudioPlayer
import com.hitit.app.service.CardSetStore
import com.hitit.app.service.DeviceOrientationService
import com.hitit.app.service.GameSessionStore
import com.hitit.app.service.PdfGenerator
import org.koin.android.ext.koin.androidContext
import org.koin.dsl.module

actual fun platformModule() = module {
    single { AppLauncher(androidContext()) }
    single { DeviceOrientationService(androidContext()) }
    single { AudioPlayer(androidContext()) }
    single { GameSessionStore(androidContext()) }
    single { CardSetStore(androidContext()) }
    single { PdfGenerator(androidContext()) }
}
