package com.safetyheads.akademiaandroida.domain.usecases

import kotlinx.coroutines.delay


class DelaySplashScreenUseCase {

    suspend fun delay() {
        delay(1000)
    }
}
