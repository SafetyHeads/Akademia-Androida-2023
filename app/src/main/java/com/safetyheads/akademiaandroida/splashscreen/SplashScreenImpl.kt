package com.safetyheads.akademiaandroida.splashscreen

import android.os.Handler
import android.os.Looper
import androidx.navigation.NavController
import com.safetyheads.akademiaandroida.R


class SplashScreenImpl: SplashScreen {

    override fun delay(navController: NavController) {
        val action = R.id.action_splashScreen_to_launchScreen

        Handler(Looper.getMainLooper()).postDelayed({
            navController.navigate(action)
        }, 3000)
    }


}