package com.safetyheads.akademiaandroida.usersessionmanager

import com.safetyheads.akademiaandroida.domain.entities.Session
import com.safetyheads.akademiaandroida.domain.entities.location.ChangeLocation
import com.safetyheads.akademiaandroida.domain.repositories.UserSessionManager
import org.koin.java.KoinJavaComponent

class ChangeLocationImpl : ChangeLocation {
    override fun sessionIsActive(): Boolean {
        val sessionManager: UserSessionManager = KoinJavaComponent.getKoin().getSessionScope().get()
        return sessionManager.isLoggedIn
    }

    override fun sessionInformation(): Session {
        return KoinJavaComponent.getKoin().getSessionScope().get()
    }
}