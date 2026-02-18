package com.hitit.app.di

import com.hitit.app.service.AppLauncher
import com.hitit.app.service.AudioPlayer
import com.hitit.app.service.CardSetStore
import com.hitit.app.service.DeviceOrientationService
import com.hitit.app.service.GameSessionStore
import com.hitit.app.service.PdfGenerator
import org.koin.dsl.module

actual fun platformModule() = module {
    single { AppLauncher() }
    single { DeviceOrientationService() }
    single { AudioPlayer() }
    single { GameSessionStore() }
    single { CardSetStore() }
    single { PdfGenerator() }
}
