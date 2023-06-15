package com.safetyheads.akademiaandroida.domain.usecases


import com.safetyheads.akademiaandroida.domain.repositories.UserRepository
import kotlinx.coroutines.flow.Flow

class UpdateProfileFcmUseCase(private val repository: UserRepository) :
    BaseUseCase<UpdateProfileFcmUseCase.UpdateProfileParam, Unit> {

    data class UpdateProfileParam(val userUUID: String, val fcmToken: String) : BaseUseCase.Params

    override suspend fun invoke(parameter: UpdateProfileParam): Flow<Result<Unit>> =
        repository.updateFcmToken(parameter.userUUID, parameter.fcmToken)
}