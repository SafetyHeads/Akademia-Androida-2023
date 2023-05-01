package com.safetyheads.akademiaandroida

import android.app.Application
import com.google.firebase.firestore.FirebaseFirestore
import com.jakewharton.threetenabp.AndroidThreeTen
import com.safetyheads.akademiaandroida.presentation.ui.career.CareerRepositoryImpl
import com.safetyheads.akademiaandroida.presentation.ui.career.CareerViewModel
import com.safetyheads.akademiaandroida.data.network.repository.FirebaseConfigRepository
import com.safetyheads.akademiaandroida.data.network.repository.CompanyInfoRepositoryImpl
import com.safetyheads.akademiaandroida.data.network.repository.FaqRepositoryImpl
import com.safetyheads.akademiaandroida.data.network.repository.TechnologyStackRepositoryImpl
import com.safetyheads.akademiaandroida.data.network.retrofit.ApiClient
import com.safetyheads.akademiaandroida.domain.repositories.ChannelRepository
import com.safetyheads.akademiaandroida.domain.repositories.CompanyInfoRepository
import com.safetyheads.akademiaandroida.domain.repositories.ConfigRepository
import com.safetyheads.akademiaandroida.domain.repositories.PlaylistRepository
import com.safetyheads.akademiaandroida.domain.repositories.TechnologyStackRepository
import com.safetyheads.akademiaandroida.domain.repositories.UserRepository
import com.safetyheads.akademiaandroida.domain.repositories.VideoRepository
import com.safetyheads.akademiaandroida.presentation.ui.activities.splashscreen.SplashScreenViewModel
import com.safetyheads.akademiaandroida.presentation.ui.customviews.dropdown.DropDownListViewModel
import com.safetyheads.akademiaandroida.presentation.ui.customviews.dropdown.LoadItemsToDropDownListUseCase
import com.safetyheads.akademiaandroida.presentation.ui.fragments.forgotpasswordfragment.ForgotPasswordViewModel
import com.safetyheads.akademiaandroida.presentation.ui.sign_up.UserRepositoryImpl
import com.safetyheads.akademiaandroida.data.network.repository.settings.SettingRepositoryImpl
import com.safetyheads.akademiaandroida.youtube.viewModel.ChannelViewModel
import com.safetyheads.akademiaandroida.youtube.viewModel.PlayListViewModel
import com.safetyheads.akademiaandroida.youtube.viewModel.VideoViewModel
import com.safetyheads.data.network.mapper.ChannelMapper
import com.safetyheads.data.network.mapper.PlayListVideoMapper
import com.safetyheads.data.network.mapper.PlaylistMapper
import com.safetyheads.data.network.mapper.VideoMapper
import com.safetyheads.data.network.repository.ChannelRepositoryImpl
import com.safetyheads.data.network.repository.PlaylistRepositoryImpl
import com.safetyheads.data.network.repository.VideoRepositoryImpl
import com.safetyheads.data.network.repository.YouTubeApiConsts
import com.safetyheads.data.network.service.YouTubeService
import com.safetyheads.akademiaandroida.domain.repositories.CareerRepository
import com.safetyheads.akademiaandroida.domain.repositories.SettingsRepository
import com.safetyheads.akademiaandroida.domain.usecases.*
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
        single<CareerRepository> { CareerRepositoryImpl() }
        single<SettingsRepository> { SettingRepositoryImpl(get()) }
        single<UserRepository> { UserRepositoryImpl() }
        single<TechnologyStackRepository> { TechnologyStackRepositoryImpl(get()) }
        single { GetTechnologyStackUseCase(get()) }
        single<CompanyInfoRepository> { CompanyInfoRepositoryImpl(get()) }
        single { FaqRepositoryImpl(get()) }

        //usecases
        single { DelaySplashScreenUseCase() }
        single { LoadItemsToDropDownListUseCase() }
        single { GetConfigUseCase(get()) }
        single { GetJobOfferUseCase(get()) }
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
        single { GetFaqUseCase(get()) }
        single { AddQuestionUseCase(get()) }

        //viewmodels
        viewModel { SplashScreenViewModel(get(), get()) }
        viewModel { DropDownListViewModel(get()) }
        viewModel { CareerViewModel(get(), get()) }
        viewModel { ForgotPasswordViewModel(get()) }
        viewModelOf(::ChannelViewModel)
        viewModelOf(::VideoViewModel)
        viewModelOf(::PlayListViewModel)
    }

    private val networkModule = module {

        single { FirebaseFirestore.getInstance() }
        single { ApiClient(BuildConfig.DEBUG) }
        //YouTubeService Singleton
        single { get<ApiClient>().create(YouTubeApiConsts.YOUTUBE_API_BASE_URL, YouTubeService::class.java) }

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