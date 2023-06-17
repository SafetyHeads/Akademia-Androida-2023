package com.safetyheads.akademiaandroida

import android.app.Application
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.messaging.FirebaseMessaging
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
import com.safetyheads.akademiaandroida.domain.entities.Session
import com.safetyheads.akademiaandroida.domain.repositories.CareerRepository
import com.safetyheads.akademiaandroida.domain.repositories.ChannelRepository
import com.safetyheads.akademiaandroida.domain.repositories.CompanyInfoRepository
import com.safetyheads.akademiaandroida.domain.repositories.ConfigRepository
import com.safetyheads.akademiaandroida.domain.repositories.FaqRepository
import com.safetyheads.akademiaandroida.domain.repositories.ImageRepository
import com.safetyheads.akademiaandroida.domain.repositories.PlaylistRepository
import com.safetyheads.akademiaandroida.domain.repositories.SettingsRepository
import com.safetyheads.akademiaandroida.domain.repositories.TechnologyStackRepository
import com.safetyheads.akademiaandroida.domain.repositories.TokenRepository
import com.safetyheads.akademiaandroida.domain.repositories.UserRepository
import com.safetyheads.akademiaandroida.domain.repositories.UserSessionManager
import com.safetyheads.akademiaandroida.domain.repositories.VideoRepository
import com.safetyheads.akademiaandroida.domain.usecases.AddImageToBitmapStorage
import com.safetyheads.akademiaandroida.domain.usecases.AddImageToUriStorage
import com.safetyheads.akademiaandroida.domain.usecases.AddImageToUserProfile
import com.safetyheads.akademiaandroida.domain.usecases.AddQuestionUseCase
import com.safetyheads.akademiaandroida.domain.usecases.AnonymousLoginUseCase
import com.safetyheads.akademiaandroida.domain.usecases.ChangeUserUseCase
import com.safetyheads.akademiaandroida.domain.usecases.DateUseCase
import com.safetyheads.akademiaandroida.domain.usecases.DateUseCaseImpl
import com.safetyheads.akademiaandroida.domain.usecases.DelaySplashScreenUseCase
import com.safetyheads.akademiaandroida.domain.usecases.GetAddressUseCase
import com.safetyheads.akademiaandroida.domain.usecases.GetAllVideoUseCase
import com.safetyheads.akademiaandroida.domain.usecases.GetChannelUseCase
import com.safetyheads.akademiaandroida.domain.usecases.GetConfigUseCase
import com.safetyheads.akademiaandroida.domain.usecases.GetContactInfoUseCase
import com.safetyheads.akademiaandroida.domain.usecases.GetFaqUseCase
import com.safetyheads.akademiaandroida.domain.usecases.GetInfoUseCase
import com.safetyheads.akademiaandroida.domain.usecases.GetInstagramImageUseCase
import com.safetyheads.akademiaandroida.domain.usecases.GetJobOfferUseCase
import com.safetyheads.akademiaandroida.domain.usecases.GetMessagingTokenUseCase
import com.safetyheads.akademiaandroida.domain.usecases.GetPlayListItemsUseCase
import com.safetyheads.akademiaandroida.domain.usecases.GetPlayListsUseCase
import com.safetyheads.akademiaandroida.domain.usecases.GetProfileInformationUseCase
import com.safetyheads.akademiaandroida.domain.usecases.GetSocialUseCase
import com.safetyheads.akademiaandroida.domain.usecases.GetTechnologyStackUseCase
import com.safetyheads.akademiaandroida.domain.usecases.GetVideoUseCase
import com.safetyheads.akademiaandroida.domain.usecases.IsLoggedInUseCase
import com.safetyheads.akademiaandroida.domain.usecases.LoginUseCase
import com.safetyheads.akademiaandroida.domain.usecases.ProfileDeleteAccountUseCase
import com.safetyheads.akademiaandroida.domain.usecases.ProfileLogOutUseCase
import com.safetyheads.akademiaandroida.domain.usecases.RegisterUseCase
import com.safetyheads.akademiaandroida.domain.usecases.RemoveImageFromStorage
import com.safetyheads.akademiaandroida.domain.usecases.RemoveImageFromUserProfile
import com.safetyheads.akademiaandroida.domain.usecases.ResetPasswordUseCase
import com.safetyheads.akademiaandroida.domain.usecases.UpdateProfileFcmUseCase
import com.safetyheads.akademiaandroida.presentation.ui.activities.splashscreen.SplashScreenViewModel
import com.safetyheads.akademiaandroida.presentation.ui.career.CareerRepositoryImpl
import com.safetyheads.akademiaandroida.presentation.ui.career.CareerViewModel
import com.safetyheads.akademiaandroida.presentation.ui.customviews.dropdown.DropDownListViewModel
import com.safetyheads.akademiaandroida.presentation.ui.customviews.dropdown.LoadItemsToDropDownListUseCase
import com.safetyheads.akademiaandroida.presentation.ui.fragments.faq.FaqViewModel
import com.safetyheads.akademiaandroida.presentation.ui.fragments.forgotpasswordfragment.ForgotPasswordViewModel
import com.safetyheads.akademiaandroida.presentation.ui.fragments.forgotpasswordfragment.usertest.UserTestViewModel
import com.safetyheads.akademiaandroida.presentation.ui.fragments.login.LoginViewModel
import com.safetyheads.akademiaandroida.presentation.ui.fragments.technologystack.TechnologyStackViewModel
import com.safetyheads.akademiaandroida.presentation.ui.fragments.youtube.ChannelViewModel
import com.safetyheads.akademiaandroida.presentation.ui.fragments.youtube.PlayListViewModel
import com.safetyheads.akademiaandroida.presentation.ui.fragments.youtube.VideoViewModel
import com.safetyheads.akademiaandroida.presentation.ui.signup.SignUpViewModel
import com.safetyheads.akademiaandroida.presentation.ui.viewmodels.DashboardViewModel
import com.safetyheads.akademiaandroida.presentation.ui.viewmodels.MediaViewModel
import com.safetyheads.akademiaandroida.presentation.ui.viewmodels.ProfileViewModel
import com.safetyheads.akademiaandroida.presentation.ui.viewmodels.LaunchScreenViewModel
import com.safetyheads.akademiaandroida.token.FirebaseTokenRepository
import com.safetyheads.akademiaandroida.usersessionmanager.LoggedSessionManager
import com.safetyheads.akademiaandroida.usersessionmanager.SESSION_SCOPE_NAME
import com.safetyheads.akademiaandroida.usersessionmanager.SessionGenerator
import com.safetyheads.akademiaandroida.usersessionmanager.UnloggedSessionManager
import com.safetyheads.akademiaandroida.usersessionmanager.UserSessionManagerViewModel
import com.safetyheads.akademiaandroida.usersessionmanager.getSessionScope
import com.safetyheads.data.network.mapper.ChannelMapper
import com.safetyheads.data.network.mapper.PlayListVideoMapper
import com.safetyheads.data.network.mapper.PlaylistMapper
import com.safetyheads.data.network.mapper.VideoMapper
import com.safetyheads.data.network.mapper.VideosMapper
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
        single { FirebaseMessaging.getInstance() }

        single { RegisterUseCase(get()) }
        //repositories
        single<ConfigRepository> { FirebaseConfigRepository() }
        single<CareerRepository> { CareerRepositoryImpl() }
        single<SettingsRepository> { SettingRepositoryImpl(get()) }
        single<UserRepository> { UserRepositoryImpl(get(), get(), get()) }
        single<TechnologyStackRepository> { TechnologyStackRepositoryImpl(get()) }
        single { GetTechnologyStackUseCase(get()) }
        single<CompanyInfoRepository> { CompanyInfoRepositoryImpl(get()) }
        single<FaqRepository> { FaqRepositoryImpl(get()) }
        single<ImageRepository> { ImageRepositoryImpl(get(), get()) }

        single<TokenRepository> { FirebaseTokenRepository(get()) }

        //usecases
        single { AddImageToUriStorage(get()) }
        single { AnonymousLoginUseCase(get()) }
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
        single { ChangeUserUseCase(get()) }
        single { ProfileDeleteAccountUseCase(get()) }
        single { ProfileLogOutUseCase(get()) }
        single { LoginUseCase(get()) }
        factory { IsLoggedInUseCase(get()) }
        single { UpdateProfileFcmUseCase(get()) }
        single { GetMessagingTokenUseCase(get()) }

        //viewmodels
        viewModel { SplashScreenViewModel(get(), get(), get()) }
        viewModel { DropDownListViewModel(get()) }
        viewModel { CareerViewModel(get(), get()) }
        viewModel { ForgotPasswordViewModel(get()) }
        viewModelOf(::ChannelViewModel)
        viewModelOf(::VideoViewModel)
        viewModelOf(::PlayListViewModel)
        viewModelOf(::ProfileViewModel)
        viewModelOf(::MediaViewModel)
        viewModelOf(::UserSessionManagerViewModel)
        viewModel { TechnologyStackViewModel(get()) }
        viewModel { SignUpViewModel(get()) }
        viewModel { FaqViewModel(get(), get()) }
        viewModel { LoginViewModel(get()) }
        viewModel { DashboardViewModel(get(), get()) }
        viewModelOf(::UserTestViewModel)
        viewModelOf(::LaunchScreenViewModel)
    }

    private val networkModule = module {

        single { ApiClient(BuildConfig.DEBUG) }
        //YouTubeService Singleton
        single { get<ApiClient>().create(YouTubeApiConsts.YOUTUBE_API_BASE_URL, YouTubeService::class.java) }

        //mapper
        single { ChannelMapper() }
        single { PlaylistMapper() }
        single { PlayListVideoMapper() }
        single { VideoMapper() }
        single { VideosMapper() }

        //repository
        single<ImageRepository> { ImageRepositoryImpl(get(), get()) }
        single<VideoRepository> { VideoRepositoryImpl(get(), get(), get(), BuildConfig.YOUTUBE_DATA_API_KEY) }
        single<ChannelRepository> { ChannelRepositoryImpl(get(), get(), BuildConfig.YOUTUBE_DATA_API_KEY) }
        single<PlaylistRepository> { PlaylistRepositoryImpl(get(), get(), get(), BuildConfig.YOUTUBE_DATA_API_KEY) }

        single { GetInstagramImageUseCase(get()) }
        single { GetAllVideoUseCase(get()) }
    }

    private val sessionModule = module {
        single { SessionGenerator(get()) }
        factory { getSessionScope().get<UserSessionManager>() }

        scope(named(SESSION_SCOPE_NAME)) {
            scoped<UserSessionManager> {
                val session = this.getOrNull<Session>()
                if (session == null || session.userEmail.isEmpty()) {
                    UnloggedSessionManager(sessionGenerator = get())
                } else {
                    LoggedSessionManager(session)
                }
            }
        }

    }

}