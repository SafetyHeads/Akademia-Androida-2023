package com.safetyheads.akademiaandroida.splashscreen

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import androidx.lifecycle.viewmodel.initializer
import androidx.lifecycle.viewmodel.viewModelFactory
import com.safetyheads.akademiaandroida.AndroidAcademyApplication
import kotlinx.coroutines.launch

class SplashScreenViewModel(private val splashScreen: SplashScreen): ViewModel() {

    val delay = viewModelScope.launch {
        splashScreen.delay()
    }

}