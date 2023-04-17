package com.safetyheads.akademiaandroida

import android.app.Application
import com.safetyheads.akademiaandroida.data.FirebaseConfigRepository
import com.safetyheads.domain.repositories.ConfigRepository
import com.safetyheads.domain.usecases.DelaySplashScreenUseCase
import com.safetyheads.domain.usecases.GetConfigUseCase
import com.safetyheads.presentation.activities.splashscreen.SplashScreenViewModel
import com.safetyheads.presentation.customviews.dropdown.DropDownListViewModel
import com.safetyheads.presentation.customviews.dropdown.LoadItemsToDropDownListUseCase
import org.koin.android.ext.koin.androidContext
import org.koin.android.ext.koin.androidLogger
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.core.context.startKoin
import org.koin.dsl.module

class AndroidAcademyApplication : Application() {

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
        viewModel { SplashScreenViewModel(get()) }
        viewModel { DropDownListViewModel(get()) }
    }
}