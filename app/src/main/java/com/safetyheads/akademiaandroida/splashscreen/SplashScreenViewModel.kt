package com.safetyheads.akademiaandroida.splashscreen

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewmodel.initializer
import androidx.lifecycle.viewmodel.viewModelFactory
import androidx.navigation.NavController
import com.safetyheads.akademiaandroida.AndroidAcademyApplication

class SplashScreenViewModel(private val splashScreen: SplashScreen): ViewModel() {

    companion object {
        val factory: ViewModelProvider.Factory = viewModelFactory {
            initializer {
                val splashScreen = (
                        this[ViewModelProvider.AndroidViewModelFactory.APPLICATION_KEY] as AndroidAcademyApplication
                        ).splashScreen
                SplashScreenViewModel(splashScreen = splashScreen)

            }
        }
    }

    fun delaySplashScreen(navController: NavController) {
        splashScreen.delay(navController)
    }

}