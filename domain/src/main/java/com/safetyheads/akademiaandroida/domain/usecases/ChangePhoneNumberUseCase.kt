package com.safetyheads.akademiaandroida.domain.usecases

import com.safetyheads.akademiaandroida.domain.repositories.UserRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow

class ChangePhoneNumberUseCase(private val repository: UserRepository) :
    BaseUseCase<ChangePhoneNumberUseCase.Param, Boolean> {

    class Param(
        val phoneNumber: String,
        val userUUID: String
    ) : BaseUseCase.Params

    override suspend fun invoke(parameter: Param): Flow<Result<Boolean>> {
        return flow {
            try {
                repository.changePhoneNumber(parameter.phoneNumber, parameter.userUUID)
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