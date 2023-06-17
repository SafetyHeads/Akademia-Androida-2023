package com.safetyheads.akademiaandroida.domain.usecases

import com.safetyheads.akademiaandroida.domain.repositories.UserRepository
import kotlinx.coroutines.flow.Flow

class ChangePasswordUseCase(private val repository: UserRepository) :
    BaseUseCase<ChangePasswordUseCase.ChangeParam, Unit> {

    data class ChangeParam(val oldPassword: String, val newPassword: String) : BaseUseCase.Params

    override suspend fun invoke(parameter: ChangeParam): Flow<Result<Unit>> {

        return repository.changePassword(parameter.oldPassword, parameter.newPassword)
    }
}
