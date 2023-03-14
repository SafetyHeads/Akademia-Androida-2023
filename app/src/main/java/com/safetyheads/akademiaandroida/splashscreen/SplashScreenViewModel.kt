package com.safetyheads.akademiaandroida.splashscreen

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import androidx.lifecycle.viewmodel.initializer
import androidx.lifecycle.viewmodel.viewModelFactory
import com.safetyheads.akademiaandroida.AndroidAcademyApplication
import kotlinx.coroutines.launch

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

    val delay = viewModelScope.launch {
        splashScreen.delay()
    }

}