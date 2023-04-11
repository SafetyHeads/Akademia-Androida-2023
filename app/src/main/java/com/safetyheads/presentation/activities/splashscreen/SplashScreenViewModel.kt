package com.safetyheads.presentation.activities.splashscreen

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.launch

class SplashScreenViewModel(private val splashScreenUseCase: SplashScreenUseCase) : ViewModel() {

    val delay = viewModelScope.launch {
        splashScreenUseCase.delay()
    }

}