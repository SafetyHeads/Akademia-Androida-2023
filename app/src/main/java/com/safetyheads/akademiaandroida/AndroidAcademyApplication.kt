package com.safetyheads.akademiaandroida

import android.app.Application
import android.util.Log
import com.safetyheads.akademiaandroida.dropdownlist.DropDownListViewModel
import com.safetyheads.akademiaandroida.dropdownlist.LoadItemsToDropDownListUseCase
import com.safetyheads.akademiaandroida.splashscreen.SplashScreenUseCase
import com.safetyheads.akademiaandroida.splashscreen.SplashScreenUseCaseImpl
import com.safetyheads.akademiaandroida.splashscreen.SplashScreenViewModel
import com.safetyheads.data.network.retrofit.ApiClient
import com.safetyheads.data.network.retrofit.AppLogger
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
        //usecases
        single<SplashScreenUseCase> { SplashScreenUseCaseImpl() }
        single { LoadItemsToDropDownListUseCase() }

        //viewmodels
        viewModel { SplashScreenViewModel(get()) }
        viewModel { DropDownListViewModel(get()) }
    }
}