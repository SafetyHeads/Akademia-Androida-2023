package com.safetyheads.akademiaandroida.domain.usecases

import com.safetyheads.akademiaandroida.domain.entities.User
import com.safetyheads.akademiaandroida.domain.repositories.UserRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow

class RegisterUseCase(
    private val repository: UserRepository
) : BaseUseCase<RegisterUseCase.UserParams, User> {
    class UserParams(val fullName: String, val email: String, val password: String) :
        BaseUseCase.Params

    override suspend fun invoke(parameter: UserParams): Flow<Result<User>> {

        return flow {
            try {
                repository.createUser(parameter.fullName, parameter.email, parameter.password)
                    .collect { user ->
                        emit(Result.success(user))
                    }
            } catch (error: IllegalStateException) {
                emit(Result.failure(error))
            }
        }
    }
}
