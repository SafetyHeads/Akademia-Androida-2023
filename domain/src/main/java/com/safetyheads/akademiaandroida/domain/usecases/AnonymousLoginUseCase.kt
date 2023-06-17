package com.safetyheads.akademiaandroida.domain.usecases

import com.safetyheads.akademiaandroida.domain.repositories.UserRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.map

class AnonymousLoginUseCase(private val repository: UserRepository) :
    ParameterlessBaseUseCase<String> {

    override suspend fun invoke(): Flow<Result<String>> {
        return repository.anonymousLogIn()
            .catch { error ->
                emit(Result.failure(error))
            }
            .map { login ->
                if (login.isSuccess)
                    Result.success(login.getOrNull().orEmpty())
                else
                    Result.failure(login.exceptionOrNull() ?: Exception("Login Error!"))
            }
    }
}