package com.hitit.app.di

import com.hitit.app.network.DeezerApiService
import com.hitit.app.repository.HitsterCardRepository
import com.hitit.app.repository.MockHitsterCardRepository
import com.hitit.app.service.DeezerMusicService
import com.hitit.app.service.MusicService
import com.hitit.app.service.SpotifyMusicService
import com.hitit.app.service.YouTubeMusicService
import com.hitit.app.ui.viewmodel.HomeViewModel
import com.hitit.app.ui.viewmodel.ScannerViewModel
import org.koin.core.module.Module
import org.koin.core.qualifier.named
import org.koin.dsl.module

expect fun platformModule(): Module

val appModule = module {
    // Repositories
    single<HitsterCardRepository> { MockHitsterCardRepository() }

    // Services - named qualifiers for different music services
    single<MusicService>(named("deezer")) { DeezerMusicService(get()) }
    single<MusicService>(named("spotify")) { SpotifyMusicService(get()) }
    single<MusicService>(named("youtube")) { YouTubeMusicService(get()) }
    // Default music service (used by HomeViewModel for check)
    single<MusicService> { DeezerMusicService(get()) }
    single { DeezerApiService() }

    // ViewModels
    factory { HomeViewModel(get(), get(), get()) }
    factory { ScannerViewModel(get(named("deezer")), get(named("spotify")), get(named("youtube")), get(), get(), get(), get(), get()) }
}

val allModules = listOf(appModule) + platformModule()
