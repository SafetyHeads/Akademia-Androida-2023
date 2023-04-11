package com.safetyheads.presentation.utils

import android.app.Application
import com.safetyheads.presentation.activities.splashscreen.SplashScreenUseCase
import com.safetyheads.presentation.activities.splashscreen.SplashScreenUseCaseImpl
import com.safetyheads.presentation.activities.splashscreen.SplashScreenViewModel
import com.safetyheads.presentation.customviews.dropdown.DropDownListViewModel
import com.safetyheads.presentation.customviews.dropdown.LoadItemsToDropDownListUseCase
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
        //usecases
        single<SplashScreenUseCase> { SplashScreenUseCaseImpl() }
        single { LoadItemsToDropDownListUseCase() }

        //viewmodels
        viewModel{ SplashScreenViewModel(get()) }
        viewModel{ DropDownListViewModel(get()) }
    }
}