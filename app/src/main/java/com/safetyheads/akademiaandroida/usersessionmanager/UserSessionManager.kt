package com.safetyheads.akademiaandroida.usersessionmanager

import org.koin.core.component.KoinComponent


sealed class UserSessionManager(val isLoggedIn: Boolean) : KoinComponent {
    abstract class Logged : UserSessionManager(true) {
        abstract val session: Session
        abstract fun logOff()
    }

    abstract class Unlogged : UserSessionManager(false) {
        abstract fun logIn()
    }
}

internal class LoggedSessionManager(override val session: Session) : UserSessionManager.Logged() {
    override fun logOff() {
        getKoin().reloadSessionScope(null)
    }
}

internal class UnloggedSessionManager(private val fakeSessionGenerator: FakeSessionGenerator) :
    UserSessionManager.Unlogged() {
    override fun logIn() {
        val fakeSession = fakeSessionGenerator.generateFakeSession()
        getKoin().reloadSessionScope(fakeSession)
    }
}