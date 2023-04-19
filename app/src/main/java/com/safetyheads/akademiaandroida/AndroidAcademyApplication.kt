package com.safetyheads.akademiaandroida

import android.app.Application
import com.safetyheads.akademiaandroida.dropdownlist.DropDownListViewModel
import com.safetyheads.akademiaandroida.dropdownlist.LoadItemsToDropDownListUseCase
import com.safetyheads.domain.usecases.DelaySplashScreenUseCase
import com.safetyheads.akademiaandroida.splashscreen.SplashScreenViewModel
import com.safetyheads.akademiaandroida.data.FirebaseConfigRepository
import com.safetyheads.domain.repositories.ConfigRepository
import com.safetyheads.domain.usecases.GetConfigUseCase
import org.koin.android.ext.koin.androidContext
import org.koin.android.ext.koin.androidLogger
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.core.context.startKoin
import org.koin.dsl.module


class AndroidAcademyApplication: Application() {

    override fun onCreate() {
        super.onCreate()
        startKoin {
            androidLogger()
            androidContext(this@AndroidAcademyApplication)
            modules(listOf(appModule, networkModule))
        }
    }

    private val networkModule = module {
        single<AppLogger> {
            object : AppLogger {
                override fun d(tag: String, message: String) {
                    Log.d(tag, message)
                }

            }
        }
        single { ApiClient(BuildConfig.DEBUG, get()) }
    }

    private val appModule = module {
        //repositories
        single<ConfigRepository> { FirebaseConfigRepository() }

        //usecases
        single { DelaySplashScreenUseCase() }
        single { LoadItemsToDropDownListUseCase() }
        single { GetConfigUseCase(get()) }

        //viewmodels
        viewModel{ SplashScreenViewModel(get(), get()) }
        viewModel{ DropDownListViewModel(get()) }
    }

    private val youtubeModule = module {
        //customLog
        single<CustomLog>{ Log() }

        //YouTubeService Singleton
        single { ApiClient(BuildConfig.DEBUG, get()).create(YouTubeApi.YOUTUBE_API_BASE_URL, YouTubeService::class.java) }

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