package com.hitit.app.di

import com.hitit.app.network.DeezerApiService
import com.hitit.app.repository.HitsterCardRepository
import com.hitit.app.repository.MockHitsterCardRepository
import com.hitit.app.service.DeezerMusicService
import com.hitit.app.service.MusicService
import com.hitit.app.ui.viewmodel.HomeViewModel
import com.hitit.app.ui.viewmodel.ScannerViewModel
import org.koin.core.module.Module
import org.koin.dsl.module

expect fun platformModule(): Module

val appModule = module {
    // Repositories
    single<HitsterCardRepository> { MockHitsterCardRepository() }

    // Services
    single<MusicService> { DeezerMusicService(get()) }
    single { DeezerApiService() }

    // ViewModels
    factory { HomeViewModel(get(), get(), get()) }
    factory { ScannerViewModel(get(), get(), get(), get(), get()) }
}

val allModules = listOf(appModule) + platformModule()
