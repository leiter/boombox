package com.hitit.app

import android.app.Application
import com.hitit.app.di.allModules
import org.koin.android.ext.koin.androidContext
import org.koin.core.context.startKoin

class HitItApplication : Application() {
    override fun onCreate() {
        super.onCreate()

        startKoin {
            androidContext(this@HitItApplication)
            modules(allModules)
        }
    }
}
