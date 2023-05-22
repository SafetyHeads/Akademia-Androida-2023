package com.safetyheads.akademiaandroida.domain.usecases


import com.safetyheads.akademiaandroida.domain.repositories.UserRepository
import kotlinx.coroutines.flow.Flow

class UpdateProfileFCMUseCase(private val repository: UserRepository) :
    BaseUseCase<UpdateProfileFCMUseCase.UpdateProfileParam, Unit> {

    data class UpdateProfileParam(val userUUID: String, val FCMToken: String) : BaseUseCase.Params

    override suspend fun invoke(parameter: UpdateProfileParam): Flow<Result<Unit>> =
        repository.updateFcmToken(parameter.userUUID, parameter.FCMToken)
}