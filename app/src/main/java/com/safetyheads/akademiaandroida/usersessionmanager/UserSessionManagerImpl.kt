package com.safetyheads.akademiaandroida.usersessionmanager

import com.safetyheads.akademiaandroida.domain.entities.Session
import com.safetyheads.akademiaandroida.domain.repositories.UserSessionManager
import org.koin.core.component.KoinComponent

sealed class UserSessionManagerImpl(override val isLoggedIn: Boolean) : UserSessionManager,
    KoinComponent {

    abstract class Logged : UserSessionManagerImpl(true) {
        abstract val session: Session
        abstract fun logOff()
    }

    abstract class Unlogged :
        UserSessionManagerImpl(false) {
        abstract fun logIn()
    }
}

class LoggedSessionManager(override val session: Session) : UserSessionManager.Logged,
    UserSessionManagerImpl.Logged() {
    override fun logOff() {
        getKoin().reloadSessionScope(Session(""))
    }
}

class UnloggedSessionManager(private val sessionGenerator: SessionGenerator) :
    UserSessionManagerImpl.Unlogged(), UserSessionManager.Unlogged, KoinComponent {
    override fun logIn() {
        val newSession = sessionGenerator.generateNewSession()
        getKoin().reloadSessionScope(newSession)
    }
}

