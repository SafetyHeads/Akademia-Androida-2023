package com.safetyheads.akademiaandroida.domain.usecases

import com.safetyheads.akademiaandroida.domain.repositories.UserSessionManager

class CreateUserSessionUseCase(private val sessionManager: UserSessionManager) {
    fun createSession() {
        when(sessionManager) {
            is UserSessionManager.Unlogged -> (sessionManager as UserSessionManager.Unlogged).logIn()
        }
    }
}