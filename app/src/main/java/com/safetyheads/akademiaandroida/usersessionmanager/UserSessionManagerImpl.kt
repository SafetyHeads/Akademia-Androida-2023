package com.safetyheads.akademiaandroida.usersessionmanager

import com.safetyheads.akademiaandroida.domain.entities.Session
import com.safetyheads.akademiaandroida.domain.repositories.UserSessionManager
import org.koin.core.component.KoinComponent

sealed class UserSessionManagerImpl(override val isLoggedIn: Boolean) : UserSessionManager, KoinComponent {

    abstract class Logged : UserSessionManager.Logged, UserSessionManagerImpl(true) {
        abstract override val session: Session
        abstract override fun logOff()
    }

    abstract class Unlogged :
        UserSessionManager.Unlogged, UserSessionManagerImpl(false) {
        abstract override fun logIn()
    }
}

class LoggedSessionManager(override val session: Session) : UserSessionManagerImpl.Logged() {
    override fun logOff() {
        getKoin().reloadSessionScope(null)
    }
}

class UnloggedSessionManager(private val sessionGenerator: SessionGenerator) :
    UserSessionManagerImpl.Unlogged(), KoinComponent {
    override fun logIn() {
        val newSession = sessionGenerator.generateNewSession()
        getKoin().reloadSessionScope(newSession)
    }
}

