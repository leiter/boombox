package com.hitit.app

import android.app.Application
import com.hitit.app.di.allModules
import org.koin.android.ext.koin.androidContext
import org.koin.core.context.startKoin

class DukeStarApplication : Application() {
    override fun onCreate() {
        super.onCreate()

        startKoin {
            androidContext(this@DukeStarApplication)
            modules(allModules)
        }
    }
}
