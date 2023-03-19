package com.safetyheads.akademiaandroida.splashscreen

import kotlinx.coroutines.delay


class SplashScreenUseCaseImpl: SplashScreenUseCase {

    override suspend fun delay() {
        delay(3000)
    }
}