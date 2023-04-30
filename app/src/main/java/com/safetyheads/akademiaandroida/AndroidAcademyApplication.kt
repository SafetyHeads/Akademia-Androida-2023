package com.safetyheads.akademiaandroida

import android.app.Application
import com.google.firebase.firestore.FirebaseFirestore
import com.jakewharton.threetenabp.AndroidThreeTen
import com.safetyheads.akademiaandroida.YouTube.viewModel.ChannelViewModel
import com.safetyheads.akademiaandroida.YouTube.viewModel.PlayListViewModel
import com.safetyheads.akademiaandroida.YouTube.viewModel.VideoViewModel
import com.safetyheads.akademiaandroida.data.FirebaseConfigRepository
import com.safetyheads.akademiaandroida.data.UserRepositoryImpl
import com.safetyheads.akademiaandroida.data.network.repository.CompanyInfoRepositoryImpl
import com.safetyheads.akademiaandroida.data.network.repository.TechnologyStackRepositoryImpl
import com.safetyheads.akademiaandroida.data.network.retrofit.ApiClient
import com.safetyheads.akademiaandroida.domain.repositories.ChannelRepository
import com.safetyheads.akademiaandroida.domain.repositories.ConfigRepository
import com.safetyheads.akademiaandroida.domain.repositories.PlaylistRepository
import com.safetyheads.akademiaandroida.domain.repositories.UserRepository
import com.safetyheads.akademiaandroida.domain.repositories.VideoRepository
import com.safetyheads.akademiaandroida.domain.usecases.DateUseCase
import com.safetyheads.akademiaandroida.domain.usecases.DateUseCaseImpl
import com.safetyheads.akademiaandroida.domain.usecases.DelaySplashScreenUseCase
import com.safetyheads.akademiaandroida.domain.usecases.GetChannelUseCase
import com.safetyheads.akademiaandroida.domain.usecases.GetConfigUseCase
import com.safetyheads.akademiaandroida.domain.usecases.GetPlayListItemsUseCase
import com.safetyheads.akademiaandroida.domain.usecases.GetPlayListsUseCase
import com.safetyheads.akademiaandroida.domain.usecases.GetVideoUseCase
import com.safetyheads.akademiaandroida.domain.usecases.ResetPasswordUseCase
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
import com.safetyheads.data.network.service.YouTubeService
import com.safetyheads.domain.repositories.CompanyInfoRepository
import com.safetyheads.domain.repositories.TechnologyStackRepository
import com.safetyheads.domain.usecases.GetAddressUseCase
import com.safetyheads.domain.usecases.GetContactInfoUseCase
import com.safetyheads.domain.usecases.GetInfoUseCase
import com.safetyheads.domain.usecases.GetSocialUseCase
import com.safetyheads.domain.usecases.GetTechnologyStackUseCase
import org.koin.android.ext.koin.androidContext
import org.koin.android.ext.koin.androidLogger
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.androidx.viewmodel.dsl.viewModelOf
import org.koin.core.context.startKoin
import org.koin.dsl.module


class AndroidAcademyApplication : Application() {

    override fun onCreate() {
        super.onCreate()
        AndroidThreeTen.init(this)

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
        single<TechnologyStackRepository> { TechnologyStackRepositoryImpl(get()) }
        single { GetTechnologyStackUseCase(get()) }
        single<CompanyInfoRepository> { CompanyInfoRepositoryImpl(get()) }

        //usecases
        single { DelaySplashScreenUseCase() }
        single { LoadItemsToDropDownListUseCase() }
        single { GetConfigUseCase(get()) }
        single { ResetPasswordUseCase(get()) }
        single { GetAddressUseCase(get()) }
        single { GetInfoUseCase(get()) }
        single { GetContactInfoUseCase(get()) }
        single { GetSocialUseCase(get()) }

        single { GetChannelUseCase(get()) }
        single { GetPlayListItemsUseCase(get()) }
        single { GetPlayListsUseCase(get()) }
        single { GetVideoUseCase(get()) }
        single<DateUseCase> { DateUseCaseImpl() }

        //viewmodels
        viewModel { SplashScreenViewModel(get(), get()) }
        viewModel { DropDownListViewModel(get()) }
        viewModel { ForgotPasswordViewModel(get()) }
        viewModelOf(::ChannelViewModel)
        viewModelOf(::VideoViewModel)
        viewModelOf(::PlayListViewModel)
    }

    private val networkModule = module {

        single { FirebaseFirestore.getInstance() }
        single { ApiClient(BuildConfig.DEBUG) }
        //YouTubeService Singleton
        single { get<ApiClient>().create(YouTubeApi.YOUTUBE_API_BASE_URL, YouTubeService::class.java) }

        //mapper
        single { ChannelMapper() }
        single { PlaylistMapper() }
        single { PlayListVideoMapper() }
        single { VideoMapper() }

        //repository
        single<VideoRepository> { VideoRepositoryImpl(get(), get(), BuildConfig.YOUTUBE_DATA_API_KEY) }
        single<ChannelRepository> { ChannelRepositoryImpl(get(), get(), BuildConfig.YOUTUBE_DATA_API_KEY) }
        single<PlaylistRepository> { PlaylistRepositoryImpl(get(), get(), get(), BuildConfig.YOUTUBE_DATA_API_KEY) }

    }


}