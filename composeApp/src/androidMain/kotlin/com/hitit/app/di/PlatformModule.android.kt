package com.hitit.app.di

import com.hitit.app.service.AppLauncher
import org.koin.android.ext.koin.androidContext
import org.koin.dsl.module

actual fun platformModule() = module {
    single { AppLauncher(androidContext()) }
}
