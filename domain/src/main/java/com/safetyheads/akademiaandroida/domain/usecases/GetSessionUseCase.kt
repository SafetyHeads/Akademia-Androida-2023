package com.safetyheads.akademiaandroida.domain.usecases

import com.safetyheads.akademiaandroida.domain.entities.Session
import kotlinx.coroutines.flow.Flow

class GetSessionUseCase() : ParameterlessBaseUseCase<Session> {
    override suspend fun invoke(): Flow<Result<Session>> {
        TODO("Not yet implemented")
    }
}