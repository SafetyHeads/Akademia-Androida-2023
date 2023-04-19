package com.safetyheads.akademiaandroida

import android.app.Application
import android.util.Log
import com.safetyheads.akademiaandroida.dropdownlist.DropDownListViewModel
import com.safetyheads.akademiaandroida.dropdownlist.LoadItemsToDropDownListUseCase
import com.safetyheads.domain.usecases.DelaySplashScreenUseCase
import com.safetyheads.akademiaandroida.splashscreen.SplashScreenViewModel
import com.safetyheads.data.network.retrofit.ApiClient
import com.safetyheads.data.network.retrofit.AppLogger
import com.safetyheads.akademiaandroida.data.FirebaseConfigRepository
import com.safetyheads.domain.repositories.ConfigRepository
import com.safetyheads.domain.usecases.GetConfigUseCase
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
            modules(listOf(appModule, networkModule))
        }
    }

    private val networkModule = module {
        single<AppLogger> {
            object : AppLogger {
                override fun d(tag: String, message: String) {
                    Log.d(tag, message)
                }

            }
        }
        single { ApiClient(BuildConfig.DEBUG, get()) }
    }

    private val appModule = module {
        //repositories
        single<ConfigRepository> { FirebaseConfigRepository() }

        //usecases
        single { DelaySplashScreenUseCase() }
        single { LoadItemsToDropDownListUseCase() }
        single { GetConfigUseCase(get()) }

        //viewmodels
        viewModel{ SplashScreenViewModel(get(), get()) }
        viewModel{ DropDownListViewModel(get()) }
    }
}