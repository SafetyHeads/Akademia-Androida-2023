package com.safetyheads.akademiaandroida.domain.usecases

import com.safetyheads.akademiaandroida.domain.repositories.UserSessionManager
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow

class GetSessionUseCase(private val userSessionManager: UserSessionManager) : ParameterlessBaseUseCase<Boolean> {
    override suspend fun invoke(): Flow<Result<Boolean>> {
        return getLoggedIn()
    }
    private fun getLoggedIn() : Flow<Result<Boolean>> = flow {
        emit(Result.success(userSessionManager.isLoggedIn))
    }
}