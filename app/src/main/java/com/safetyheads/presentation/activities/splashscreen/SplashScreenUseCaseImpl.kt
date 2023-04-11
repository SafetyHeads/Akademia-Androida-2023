package com.safetyheads.presentation.activities.splashscreen

class SplashScreenUseCaseImpl : SplashScreenUseCase {

    override suspend fun delay() {
        kotlinx.coroutines.delay(3000)
    }
}