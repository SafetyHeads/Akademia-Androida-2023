package com.safetyheads.akademiaandroida

import android.app.Application
import com.safetyheads.akademiaandroida.data.FirebaseConfigRepository
import com.safetyheads.akademiaandroida.data.UserRepositoryImpl
import com.safetyheads.akademiaandroida.dropdownlist.DropDownListViewModel
import com.safetyheads.akademiaandroida.dropdownlist.LoadItemsToDropDownListUseCase
import com.safetyheads.akademiaandroida.forgotpasswordfragment.ForgotPasswordViewModel
import com.safetyheads.akademiaandroida.splashscreen.SplashScreenViewModel
import com.safetyheads.domain.repositories.ConfigRepository
import com.safetyheads.domain.repositories.UserRepository
import com.safetyheads.domain.usecases.DelaySplashScreenUseCase
import com.safetyheads.domain.usecases.GetConfigUseCase
import com.safetyheads.domain.usecases.ResetPasswordUseCase
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
            modules(appModule)
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
}