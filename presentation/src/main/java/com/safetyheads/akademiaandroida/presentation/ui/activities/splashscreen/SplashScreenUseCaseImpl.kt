package com.safetyheads.akademiaandroida.presentation.ui.activities.splashscreen

class SplashScreenUseCaseImpl : SplashScreenUseCase {

    override suspend fun delay() {
        kotlinx.coroutines.delay(3000)
    }
}