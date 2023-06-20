package com.safetyheads.akademiaandroida.domain.usecases

import com.safetyheads.akademiaandroida.domain.entities.Session
import com.safetyheads.akademiaandroida.domain.repositories.UserSessionManager
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow

class GetSessionUseCase(private val userSessionManager: UserSessionManager) :
    ParameterlessBaseUseCase<Session> {
    override suspend fun invoke(): Flow<Result<Session>> {
        return getSession()
    }

    private fun getSession(): Flow<Result<Session>> = flow {
        val session = (userSessionManager as? UserSessionManager.Logged)?.session
        if (session != null) {
            emit(Result.success(session))
        } else {
            emit(Result.failure(Exception("Session is null!")))
        }
    }
}