package com.safetyheads.akademiaandroida.domain.usecases

import com.safetyheads.akademiaandroida.domain.entities.User
import com.safetyheads.akademiaandroida.domain.repositories.UserRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow


class LoginUseCase(
    private val repository: UserRepository
) : BaseUseCase<LoginUseCase.UserParams, User> {
    class UserParams(val email: String, val password: String) :
        BaseUseCase.Params

    override suspend fun invoke(parameter: UserParams): Flow<Result<User>> {
        return flow {
            try {
                repository.loginUser(parameter.email, parameter.password)
                    .collect { user ->
                        emit(Result.success(user))
                    }
            } catch (error: IllegalStateException) {
                emit(Result.failure(error))
            }
        }
    }
}
