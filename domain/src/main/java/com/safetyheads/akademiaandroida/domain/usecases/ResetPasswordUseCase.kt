package com.safetyheads.akademiaandroida.domain.usecases

import com.safetyheads.akademiaandroida.domain.entities.ResetPassword
import com.safetyheads.akademiaandroida.domain.repositories.UserRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow

class ResetPasswordUseCase(private val repository: UserRepository) :
    BaseUseCase<ResetPasswordUseCase.ResetParam, ResetPassword> {

    @JvmInline
    value class ResetParam(val email: String) : BaseUseCase.Params

    override suspend fun invoke(parameter: ResetParam): Flow<Result<ResetPassword>> {
        return flow {
            try {
                repository.resetPassword(parameter.email).collect { resetPasswordEntity ->
                    if (resetPasswordEntity.isSuccess)
                        emit(Result.success(resetPasswordEntity))
                    else
                        emit(
                            Result.failure(
                                resetPasswordEntity.error ?: Exception("Reset password failed")
                            )
                        )
                }
            } catch (error: IllegalStateException) {
                emit(Result.failure(error))
            }
        }
    }
}
