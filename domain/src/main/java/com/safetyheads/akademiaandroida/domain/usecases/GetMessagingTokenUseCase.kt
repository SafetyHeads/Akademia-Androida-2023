package com.safetyheads.akademiaandroida.domain.usecases

import com.safetyheads.akademiaandroida.domain.repositories.TokenRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.map

class GetMessagingTokenUseCase(private val repository: TokenRepository) :
    ParameterlessBaseUseCase<String> {
    override suspend fun invoke(): Flow<Result<String>> = repository.getMessagingToken()
        .map { token -> Result.success(token) }
        .catch { exception -> emit(Result.failure(exception)) }
}