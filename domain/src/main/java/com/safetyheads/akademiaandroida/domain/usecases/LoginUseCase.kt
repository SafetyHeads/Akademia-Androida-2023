package com.safetyheads.akademiaandroida.domain.usecases

import com.safetyheads.akademiaandroida.domain.repositories.UserRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.map

class LoginUseCase(private val repository: UserRepository) :
    BaseUseCase<LoginUseCase.LoginParam, String> {

    class LoginParam(val email: String, val password: String) : BaseUseCase.Params

    override suspend fun invoke(parameter: LoginParam): Flow<Result<String>> {
        return repository.logIn(parameter.email, parameter.password)
            .catch { error ->
                emit(Result.failure(error))
            }
            .map { login ->
                if (login.isSuccess)
                    Result.success(login.getOrNull()!!)
                else
                    Result.failure(login.exceptionOrNull() ?: Exception("Login Error!"))
            }
    }
}