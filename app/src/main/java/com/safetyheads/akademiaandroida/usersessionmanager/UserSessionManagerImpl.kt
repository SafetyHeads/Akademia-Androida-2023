package com.safetyheads.akademiaandroida.usersessionmanager

import com.safetyheads.akademiaandroida.domain.repositories.UserSessionManager
import org.koin.core.component.KoinComponent


sealed class UserSessionManagerImpl(override val isLoggedIn: Boolean) : UserSessionManager, KoinComponent {
    abstract class Logged : UserSessionManagerImpl(true) {
        abstract val session: Session
        abstract fun logOff()
    }

    abstract class Unlogged : UserSessionManagerImpl(false) {
        abstract fun logIn()
    }
}

internal class LoggedSessionManagerImpl(override val session: Session) : UserSessionManagerImpl.Logged() {
    override fun logOff() {
        getKoin().reloadSessionScope(null)
    }
}

internal class UnloggedSessionManagerImpl(private val fakeSessionGenerator: FakeSessionGenerator) :
    UserSessionManagerImpl.Unlogged() {
    override fun logIn() {
        val fakeSession = fakeSessionGenerator.generateFakeSession()
        getKoin().reloadSessionScope(fakeSession)
    }
}