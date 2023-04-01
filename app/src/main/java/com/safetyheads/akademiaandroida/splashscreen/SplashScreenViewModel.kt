package com.safetyheads.akademiaandroida.splashscreen

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.safetyheads.domain.usecases.GetConfigUseCase
import kotlinx.coroutines.launch

class SplashScreenViewModel(
    private val splashScreenUseCase: SplashScreenUseCase,
    private val getConfigUseCase: GetConfigUseCase
    ): ViewModel() {

    val getConfig = viewModelScope.launch {
        getConfigUseCase.invoke()
        splashScreenUseCase.delay()
    }

}