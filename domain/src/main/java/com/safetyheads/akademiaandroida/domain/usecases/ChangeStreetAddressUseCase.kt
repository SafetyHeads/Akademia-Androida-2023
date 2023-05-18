package com.safetyheads.akademiaandroida.domain.usecases

import com.safetyheads.akademiaandroida.domain.repositories.UserRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow

class ChangeStreetAddressUseCase(private val repository: UserRepository) :
    BaseUseCase<ChangeStreetAddressUseCase.Param, Boolean> {

    class Param(
        val streetName: String,
        val streetNumber: String,
        val userUUID: String
    ) : BaseUseCase.Params

    override suspend fun invoke(parameter: Param): Flow<Result<Boolean>> {
        return flow {
            try {
                repository.changeStreetAddress(parameter.streetName, parameter.streetNumber, parameter.userUUID)
                    .collect { result ->
                        if (result.isSuccess)
                            emit(result)
                        else
                            emit(
                                Result.failure(
                                    result.exceptionOrNull()
                                        ?: Exception("Firebase Firestore Change!")
                                )
                            )
                    }
            } catch (error: IllegalStateException) {
                emit(Result.failure(error))
            }
        }
    }
}