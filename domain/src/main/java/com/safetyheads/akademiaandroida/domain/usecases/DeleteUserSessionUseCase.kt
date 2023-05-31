package com.safetyheads.akademiaandroida.domain.usecases

import com.safetyheads.akademiaandroida.domain.repositories.UserSessionManager

class DeleteUserSessionUseCase(private val sessionManager: UserSessionManager) {
    fun deleteSession() {
        when(sessionManager) {
            is UserSessionManager.Logged -> (sessionManager as UserSessionManager.Logged).logOff()
        }
    }
}