package com.safetyheads.akademiaandroida.domain.usecases

import com.safetyheads.akademiaandroida.domain.repositories.UserRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow

class LoginUseCase(private val repository: UserRepository) :
    BaseUseCase<LoginUseCase.LoginParam, String> {

    class LoginParam(val email: String, val password: String) : BaseUseCase.Params

    override suspend fun invoke(parameter: LoginParam): Flow<Result<String>> {
        return flow {
            try {
                repository.logIn(parameter.email, parameter.password).collect { login ->
                    if (login.isSuccess)
                        emit(login)
                    else
                        emit(
                            Result.failure(
                                login.exceptionOrNull() ?: Exception("Login Error!")
                            )
                        )
                }
            } catch (error: IllegalStateException) {
                emit(Result.failure(error))
            }
        }
    }
}