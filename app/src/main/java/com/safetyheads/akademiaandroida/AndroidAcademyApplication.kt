package com.safetyheads.akademiaandroida

import android.app.Application
import com.safetyheads.akademiaandroida.YouTube.useCases.ChannelUseCase
import com.safetyheads.akademiaandroida.YouTube.useCases.ChannelUseCaseImpl
import com.safetyheads.akademiaandroida.YouTube.useCases.DateUseCase
import com.safetyheads.akademiaandroida.YouTube.useCases.DateUseCaseImpl
import com.safetyheads.akademiaandroida.YouTube.useCases.PlayListItemsUseCase
import com.safetyheads.akademiaandroida.YouTube.useCases.PlayListItemsUseCaseImpl
import com.safetyheads.akademiaandroida.YouTube.useCases.PlayListsUseCase
import com.safetyheads.akademiaandroida.YouTube.useCases.PlayListsUseCaseImpl
import com.safetyheads.akademiaandroida.YouTube.useCases.VideoUseCase
import com.safetyheads.akademiaandroida.YouTube.useCases.VideoUseCaseImpl
import com.safetyheads.akademiaandroida.YouTube.viewModel.ChannelViewModel
import com.safetyheads.akademiaandroida.YouTube.viewModel.PlayListViewModel
import com.safetyheads.akademiaandroida.YouTube.viewModel.VideoViewModel
import com.safetyheads.akademiaandroida.dropdownlist.DropDownListViewModel
import com.safetyheads.akademiaandroida.dropdownlist.LoadItemsToDropDownListUseCase
import com.safetyheads.akademiaandroida.splashscreen.SplashScreenUseCase
import com.safetyheads.akademiaandroida.splashscreen.SplashScreenUseCaseImpl
import com.safetyheads.akademiaandroida.splashscreen.SplashScreenViewModel
import com.safetyheads.data.network.retrofit.ApiClient
import com.safetyheads.data.`object`.YouTubeApi
import com.safetyheads.data.repository.ChannelRepository
import com.safetyheads.data.repository.ChannelRepositoryImpl
import com.safetyheads.data.repository.PlaylistRepository
import com.safetyheads.data.repository.PlaylistRepositoryImpl
import com.safetyheads.data.repository.VideoRepository
import com.safetyheads.data.repository.VideoRepositoryImpl
import com.safetyheads.data.service.YouTubeService
import org.koin.android.ext.koin.androidContext
import org.koin.android.ext.koin.androidLogger
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.androidx.viewmodel.dsl.viewModelOf
import org.koin.core.context.startKoin
import org.koin.dsl.module


class AndroidAcademyApplication: Application() {

    override fun onCreate() {
        super.onCreate()
        startKoin {
            androidLogger()
            androidContext(this@AndroidAcademyApplication)
            modules(appModule)
            modules(youtubeModule)
        }
    }

    private val appModule = module {
        //usecases
        single<SplashScreenUseCase> { SplashScreenUseCaseImpl() }
        single { LoadItemsToDropDownListUseCase() }

        //viewmodels
        viewModel{ SplashScreenViewModel(get()) }
        viewModel{ DropDownListViewModel(get()) }
    }

    private val youtubeModule = module {
        //YouTubeService Singleton
        single { ApiClient().create(YouTubeApi.YOUTUBE_API_BASE_URL, YouTubeService::class.java) }

        //repository
        single<VideoRepository>{ VideoRepositoryImpl(get()) }
        single<ChannelRepository>{ ChannelRepositoryImpl(get()) }
        single<PlaylistRepository>{ PlaylistRepositoryImpl(get()) }

        //usecases
        single<VideoUseCase>{ VideoUseCaseImpl(get()) }
        single<DateUseCase>{ DateUseCaseImpl() }
        single<ChannelUseCase>{ ChannelUseCaseImpl(get()) }
        single<PlayListsUseCase>{ PlayListsUseCaseImpl(get()) }
        single<PlayListItemsUseCase>{ PlayListItemsUseCaseImpl(get()) }

        //viewmodels
        viewModelOf(::ChannelViewModel)
        viewModelOf(::VideoViewModel)
        viewModelOf(::PlayListViewModel)
    }
}