package com.safetyheads.akademiaandroida

import android.app.Application
import com.safetyheads.akademiaandroida.dropdownlist.DropDownListViewModel
import com.safetyheads.akademiaandroida.dropdownlist.LoadItemsToDropDownListUseCase
import com.safetyheads.akademiaandroida.splashscreen.SplashScreenUseCase
import com.safetyheads.akademiaandroida.splashscreen.SplashScreenUseCaseImpl
import com.safetyheads.akademiaandroida.splashscreen.SplashScreenViewModel
import com.safetyheads.domain.repositories.UserRepository
import com.safetyheads.domain.repositories.UserRepositoryImpl
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
        //repository
        single<UserRepository> { UserRepositoryImpl() }

        //usecases
        single<SplashScreenUseCase> { SplashScreenUseCaseImpl() }
        single { LoadItemsToDropDownListUseCase() }
        single { ResetPasswordUseCase(get()) }

        //viewmodels
        viewModel { SplashScreenViewModel(get()) }
        viewModel { DropDownListViewModel(get()) }

    }
}