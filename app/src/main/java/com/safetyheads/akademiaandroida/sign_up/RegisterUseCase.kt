package com.safetyheads.akademiaandroida.sign_up

import com.safetyheads.domain.usecases.BaseUseCase
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow

class RegisterUseCase(
    private val repository: UserRepository
) : BaseUseCase<RegisterUseCase.UserParams, User> {
    class UserParams(val fullName: String, val email: String, val password: String): BaseUseCase.Params
    override suspend fun invoke(parameter: RegisterUseCase.UserParams): Flow<Result<User>> {

            return flow {
                try {
                    repository.createUser(parameter.fullName, parameter.email, parameter.password).collect{ user ->
                        emit(Result.success(user))
                    }
                }   catch (error: Exception) {
                    emit(Result.failure(error))
                }
            }
    }
}