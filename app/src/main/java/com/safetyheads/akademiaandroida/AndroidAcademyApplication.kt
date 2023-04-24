package com.safetyheads.akademiaandroida

import android.app.Application
import android.util.Log
import com.safetyheads.akademiaandroida.YouTube.viewModel.ChannelViewModel
import com.safetyheads.akademiaandroida.YouTube.viewModel.PlayListViewModel
import com.safetyheads.akademiaandroida.YouTube.viewModel.VideoViewModel
import com.safetyheads.akademiaandroida.data.FirebaseConfigRepository
import com.safetyheads.akademiaandroida.data.UserRepositoryImpl
import com.safetyheads.akademiaandroida.dropdownlist.DropDownListViewModel
import com.safetyheads.akademiaandroida.dropdownlist.LoadItemsToDropDownListUseCase
import com.safetyheads.akademiaandroida.forgotpasswordfragment.ForgotPasswordViewModel
import com.safetyheads.akademiaandroida.splashscreen.SplashScreenViewModel
import com.safetyheads.data.network.mapper.ChannelMapper
import com.safetyheads.data.network.mapper.PlayListVideoMapper
import com.safetyheads.data.network.mapper.PlaylistMapper
import com.safetyheads.data.network.mapper.VideoMapper
import com.safetyheads.data.network.`object`.YouTubeApi
import com.safetyheads.data.network.repository.ChannelRepositoryImpl
import com.safetyheads.data.network.repository.PlaylistRepositoryImpl
import com.safetyheads.data.network.repository.VideoRepositoryImpl
import com.safetyheads.data.network.retrofit.ApiClient
import com.safetyheads.data.network.service.YouTubeService
import com.safetyheads.domain.repositories.ChannelRepository
import com.safetyheads.domain.repositories.ConfigRepository
import com.safetyheads.domain.repositories.PlaylistRepository
import com.safetyheads.domain.repositories.VideoRepository
import com.safetyheads.domain.usecases.DateUseCase
import com.safetyheads.domain.usecases.DateUseCaseImpl
import com.safetyheads.domain.usecases.DelaySplashScreenUseCase
import com.safetyheads.domain.usecases.GetChannelUseCase
import com.safetyheads.domain.repositories.UserRepository
import com.safetyheads.domain.usecases.GetConfigUseCase
import com.safetyheads.domain.usecases.GetPlayListItemsUseCase
import com.safetyheads.domain.usecases.GetPlayListsUseCase
import com.safetyheads.domain.usecases.GetVideoUseCase
import com.safetyheads.domain.usecases.ResetPasswordUseCase
import org.koin.android.ext.koin.androidContext
import org.koin.android.ext.koin.androidLogger
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.androidx.viewmodel.dsl.viewModelOf
import org.koin.core.context.startKoin
import org.koin.dsl.module


class AndroidAcademyApplication : Application() {

    override fun onCreate() {
        super.onCreate()
        startKoin {
            androidLogger()
            androidContext(this@AndroidAcademyApplication)
            modules(listOf(appModule, networkModule))
        }
    }

    private val appModule = module {
        //repositories
        single<ConfigRepository> { FirebaseConfigRepository() }
        single<UserRepository> { UserRepositoryImpl() }

        //usecases
        single { DelaySplashScreenUseCase() }
        single { LoadItemsToDropDownListUseCase() }
        single { GetConfigUseCase(get()) }
        single { ResetPasswordUseCase(get()) }

        //viewmodels
        viewModel { SplashScreenViewModel(get(), get()) }
        viewModel { DropDownListViewModel(get()) }
        viewModel { ForgotPasswordViewModel(get()) }
    }

    private val networkModule = module {
        //YouTubeService Singleton
        single { ApiClient(BuildConfig.DEBUG).create(YouTubeApi.YOUTUBE_API_BASE_URL, YouTubeService::class.java) }

        //mapper
        single { ChannelMapper() }
        single { PlaylistMapper() }
        single { PlayListVideoMapper() }
        single { VideoMapper() }

        //repository
        single<VideoRepository>{ VideoRepositoryImpl(get(), get(), BuildConfig.YOUTUBE_DATA_API_KEY) }
        single<ChannelRepository>{ ChannelRepositoryImpl(get(), get(), BuildConfig.YOUTUBE_DATA_API_KEY) }
        single<PlaylistRepository>{ PlaylistRepositoryImpl(get(), get(), get(), BuildConfig.YOUTUBE_DATA_API_KEY) }

        //usecases
        single { GetChannelUseCase(get()) }
        single { GetPlayListItemsUseCase(get()) }
        single { GetPlayListsUseCase(get()) }
        single { GetVideoUseCase(get()) }
        single<DateUseCase>{ DateUseCaseImpl() }

        //viewmodels
        viewModelOf(::ChannelViewModel)
        viewModelOf(::VideoViewModel)
        viewModelOf(::PlayListViewModel)
    }
}