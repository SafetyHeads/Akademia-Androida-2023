package com.safetyheads.akademiaandroida.splashscreen

import kotlinx.coroutines.delay


class SplashScreenImpl: SplashScreen {

    override suspend fun delay() {
        delay(3000)
    }


}