package com.safetyheads.akademiaandroida

import android.app.Application
import com.safetyheads.akademiaandroida.splashscreen.SplashScreen
import com.safetyheads.akademiaandroida.splashscreen.SplashScreenImpl

class AndroidAcademyApplication: Application() {

    lateinit var splashScreen: SplashScreen

    override fun onCreate() {
        super.onCreate()

        splashScreen = SplashScreenImpl()
    }
}