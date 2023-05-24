package com.safetyheads.akademiaandroida

import android.app.Application
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.storage.FirebaseStorage
import com.jakewharton.threetenabp.AndroidThreeTen
import com.safetyheads.akademiaandroida.data.network.repository.CompanyInfoRepositoryImpl
import com.safetyheads.akademiaandroida.data.network.repository.FaqRepositoryImpl
import com.safetyheads.akademiaandroida.data.network.repository.FirebaseConfigRepository
import com.safetyheads.akademiaandroida.data.network.repository.ImageRepositoryImpl
import com.safetyheads.akademiaandroida.data.network.repository.TechnologyStackRepositoryImpl
import com.safetyheads.akademiaandroida.data.network.repository.UserRepositoryImpl
import com.safetyheads.akademiaandroida.data.network.repository.settings.SettingRepositoryImpl
import com.safetyheads.akademiaandroida.data.network.retrofit.ApiClient
import com.safetyheads.akademiaandroida.domain.repositories.CareerRepository
import com.safetyheads.akademiaandroida.domain.repositories.ChannelRepository
import com.safetyheads.akademiaandroida.domain.repositories.CompanyInfoRepository
import com.safetyheads.akademiaandroida.domain.repositories.ConfigRepository
import com.safetyheads.akademiaandroida.domain.repositories.FaqRepository
import com.safetyheads.akademiaandroida.domain.repositories.ImageRepository
import com.safetyheads.akademiaandroida.domain.repositories.PlaylistRepository
import com.safetyheads.akademiaandroida.domain.repositories.SettingsRepository
import com.safetyheads.akademiaandroida.domain.repositories.TechnologyStackRepository
import com.safetyheads.akademiaandroida.domain.repositories.UserRepository
import com.safetyheads.akademiaandroida.domain.repositories.VideoRepository
import com.safetyheads.akademiaandroida.domain.usecases.AddImageToBitmapStorage
import com.safetyheads.akademiaandroida.domain.usecases.AddImageToUriStorage
import com.safetyheads.akademiaandroida.domain.usecases.AddImageToUserProfile
import com.safetyheads.akademiaandroida.domain.usecases.AddQuestionUseCase
import com.safetyheads.akademiaandroida.domain.usecases.DateUseCase
import com.safetyheads.akademiaandroida.domain.usecases.DateUseCaseImpl
import com.safetyheads.akademiaandroida.domain.usecases.DelaySplashScreenUseCase
import com.safetyheads.akademiaandroida.domain.usecases.GetAddressUseCase
import com.safetyheads.akademiaandroida.domain.usecases.GetChannelUseCase
import com.safetyheads.akademiaandroida.domain.usecases.GetConfigUseCase
import com.safetyheads.akademiaandroida.domain.usecases.GetContactInfoUseCase
import com.safetyheads.akademiaandroida.domain.usecases.GetFaqUseCase
import com.safetyheads.akademiaandroida.domain.usecases.GetInfoUseCase
import com.safetyheads.akademiaandroida.domain.usecases.GetJobOfferUseCase
import com.safetyheads.akademiaandroida.domain.usecases.GetPlayListItemsUseCase
import com.safetyheads.akademiaandroida.domain.usecases.GetPlayListsUseCase
import com.safetyheads.akademiaandroida.domain.usecases.GetProfileInformationUseCase
import com.safetyheads.akademiaandroida.domain.usecases.GetSocialUseCase
import com.safetyheads.akademiaandroida.domain.usecases.GetTechnologyStackUseCase
import com.safetyheads.akademiaandroida.domain.usecases.GetVideoUseCase
import com.safetyheads.akademiaandroida.domain.usecases.LoginUseCase
import com.safetyheads.akademiaandroida.domain.usecases.RegisterUseCase
import com.safetyheads.akademiaandroida.domain.usecases.RemoveImageFromStorage
import com.safetyheads.akademiaandroida.domain.usecases.RemoveImageFromUserProfile
import com.safetyheads.akademiaandroida.domain.usecases.ResetPasswordUseCase
import com.safetyheads.akademiaandroida.presentation.ui.activities.splashscreen.SplashScreenViewModel
import com.safetyheads.akademiaandroida.presentation.ui.career.CareerRepositoryImpl
import com.safetyheads.akademiaandroida.presentation.ui.career.CareerViewModel
import com.safetyheads.akademiaandroida.presentation.ui.customviews.dropdown.DropDownListViewModel
import com.safetyheads.akademiaandroida.presentation.ui.customviews.dropdown.LoadItemsToDropDownListUseCase
import com.safetyheads.akademiaandroida.presentation.ui.fragments.faq.FaqViewModel
import com.safetyheads.akademiaandroida.presentation.ui.fragments.forgotpasswordfragment.ForgotPasswordViewModel
import com.safetyheads.akademiaandroida.presentation.ui.fragments.login.LoginViewModel
import com.safetyheads.akademiaandroida.presentation.ui.fragments.technologystack.TechnologyStackViewModel
import com.safetyheads.akademiaandroida.presentation.ui.fragments.youtube.ChannelViewModel
import com.safetyheads.akademiaandroida.presentation.ui.fragments.youtube.PlayListViewModel
import com.safetyheads.akademiaandroida.presentation.ui.fragments.youtube.VideoViewModel
import com.safetyheads.akademiaandroida.presentation.ui.signup.SignUpViewModel
import com.safetyheads.akademiaandroida.presentation.ui.viewmodels.ProfileViewModel
import com.safetyheads.akademiaandroida.usersessionmanager.FakeSessionGenerator
import com.safetyheads.akademiaandroida.usersessionmanager.LoggedSessionManager
import com.safetyheads.akademiaandroida.usersessionmanager.SESSION_SCOPE_NAME
import com.safetyheads.akademiaandroida.usersessionmanager.Session
import com.safetyheads.akademiaandroida.usersessionmanager.UnloggedSessionManager
import com.safetyheads.akademiaandroida.usersessionmanager.getSessionScope
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
import org.koin.android.ext.koin.androidContext
import org.koin.android.ext.koin.androidLogger
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.androidx.viewmodel.dsl.viewModelOf
import org.koin.core.context.startKoin
import org.koin.core.qualifier.named
import org.koin.dsl.module


class AndroidAcademyApplication : Application() {

    override fun onCreate() {
        super.onCreate()
        AndroidThreeTen.init(this)

        startKoin {
            androidLogger()
            androidContext(this@AndroidAcademyApplication)
            modules(listOf(appModule, networkModule, sessionModule))
        }
    }

    private val appModule = module {

        single { FirebaseAuth.getInstance() }
        single { FirebaseFirestore.getInstance() }
        single { FirebaseStorage.getInstance() }


        single { RegisterUseCase(get()) }
        viewModel { SignUpViewModel(get()) }
        //repositories
        single<ConfigRepository> { FirebaseConfigRepository() }
        single<CareerRepository> { CareerRepositoryImpl() }
        single<SettingsRepository> { SettingRepositoryImpl(get()) }
        single<UserRepository> { UserRepositoryImpl(get(), get()) }
        single<TechnologyStackRepository> { TechnologyStackRepositoryImpl(get()) }
        single { GetTechnologyStackUseCase(get()) }
        single<CompanyInfoRepository> { CompanyInfoRepositoryImpl(get()) }
        single<FaqRepository> { FaqRepositoryImpl(get()) }
        single<ImageRepository> { ImageRepositoryImpl(get(), get()) }


        //usecases
        single { AddImageToUriStorage(get()) }
        single { AddImageToBitmapStorage(get()) }
        single { AddImageToUserProfile(get()) }
        single { RemoveImageFromUserProfile(get()) }
        single { RemoveImageFromStorage(get()) }
        single { GetProfileInformationUseCase(get()) }
        single { LoginUseCase(get()) }
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
        single { GetTechnologyStackUseCase(get()) }
        single { RegisterUseCase(get()) }
        single { LoginUseCase(get()) }

        //viewmodels
        viewModel { SplashScreenViewModel(get(), get()) }
        viewModel { DropDownListViewModel(get()) }
        viewModel { CareerViewModel(get(), get()) }
        viewModel { ForgotPasswordViewModel(get()) }
        viewModelOf(::ChannelViewModel)
        viewModelOf(::VideoViewModel)
        viewModelOf(::PlayListViewModel)
        viewModelOf(::ProfileViewModel)
        viewModel { TechnologyStackViewModel(get()) }
        viewModel { SignUpViewModel(get()) }
        viewModel { FaqViewModel(get(), get()) }
        viewModel { LoginViewModel(get()) }
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

    private val sessionModule = module {
        single { FakeSessionGenerator() }

        scope(named(SESSION_SCOPE_NAME)) {
            scoped {
                val session = getSessionScope().getOrNull<Session>()
                if (session == null) {
                    UnloggedSessionManager(fakeSessionGenerator = get())
                } else {
                    LoggedSessionManager(session)
                }
            }
        }

    }

}