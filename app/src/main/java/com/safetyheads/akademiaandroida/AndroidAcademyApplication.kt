package com.safetyheads.akademiaandroida

import android.app.Application
import com.safetyheads.akademiaandroida.data.FirebaseConfigRepository
import com.safetyheads.akademiaandroida.data.UserRepositoryImpl
import com.safetyheads.akademiaandroida.dropdownlist.DropDownListViewModel
import com.safetyheads.akademiaandroida.dropdownlist.LoadItemsToDropDownListUseCase
import com.safetyheads.akademiaandroida.forgotpasswordfragment.ForgotPasswordViewModel
import com.safetyheads.akademiaandroida.splashscreen.SplashScreenViewModel
import com.safetyheads.akademiaandroida.usersessionmanager.*
import com.safetyheads.akademiaandroida.usersessionmanager.FakeSessionGenerator
import com.safetyheads.akademiaandroida.usersessionmanager.LoggedSessionManager
import com.safetyheads.akademiaandroida.usersessionmanager.SESSION_SCOPE_NAME
import com.safetyheads.akademiaandroida.usersessionmanager.UnloggedSessionManager
import com.safetyheads.data.network.retrofit.ApiClient
import com.safetyheads.domain.repositories.ConfigRepository
import com.safetyheads.domain.repositories.UserRepository
import com.safetyheads.domain.usecases.DelaySplashScreenUseCase
import com.safetyheads.domain.usecases.GetConfigUseCase
import com.safetyheads.domain.usecases.ResetPasswordUseCase
import org.koin.android.ext.koin.androidContext
import org.koin.android.ext.koin.androidLogger
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.core.context.startKoin
import org.koin.core.qualifier.named
import org.koin.dsl.module


class AndroidAcademyApplication : Application() {

    override fun onCreate() {
        super.onCreate()
        startKoin {
            androidLogger()
            androidContext(this@AndroidAcademyApplication)
            modules(listOf(appModule, networkModule, sessionModule))
        }
    }

    private val networkModule = module {
        single { ApiClient(BuildConfig.DEBUG) }
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
