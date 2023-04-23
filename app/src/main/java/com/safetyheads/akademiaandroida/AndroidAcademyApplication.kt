package com.safetyheads.akademiaandroida

import android.app.Application
import com.safetyheads.akademiaandroida.career.CareerRepositoryImpl
import com.safetyheads.akademiaandroida.career.CareerViewModel
import com.safetyheads.akademiaandroida.dropdownlist.DropDownListViewModel
import com.safetyheads.akademiaandroida.dropdownlist.LoadItemsToDropDownListUseCase
import com.safetyheads.domain.usecases.DelaySplashScreenUseCase
import com.safetyheads.akademiaandroida.splashscreen.SplashScreenViewModel
import com.safetyheads.akademiaandroida.data.FirebaseConfigRepository
import com.safetyheads.akademiaandroida.settings.SettingRepositoryImpl
import com.safetyheads.domain.repositories.CareerRepository
import com.safetyheads.domain.repositories.ConfigRepository
import com.safetyheads.domain.repositories.SettingsRepository
import com.safetyheads.domain.usecases.GetConfigUseCase
import com.safetyheads.domain.usecases.GetJobOfferUseCase
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
        single<CareerRepository> { CareerRepositoryImpl() }
        single<SettingsRepository> { SettingRepositoryImpl(get()) }

        //usecases
        single { DelaySplashScreenUseCase() }
        single { LoadItemsToDropDownListUseCase() }
        single { GetConfigUseCase(get()) }
        single { GetJobOfferUseCase(get()) }

        //viewmodels
        viewModel{ SplashScreenViewModel(get(), get()) }
        viewModel{ DropDownListViewModel(get()) }
        viewModel{ CareerViewModel(get(), get()) }
    }
}