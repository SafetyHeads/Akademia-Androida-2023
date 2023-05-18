package com.safetyheads.akademiaandroida.domain.usecases

import com.safetyheads.akademiaandroida.domain.repositories.UserRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow

class ChangeCountryAddressUseCase(private val repository: UserRepository) :
    BaseUseCase<ChangeCountryAddressUseCase.Param, Boolean> {

    class Param(val country: String, val userUUID: String) : BaseUseCase.Params

    override suspend fun invoke(parameter: Param): Flow<Result<Boolean>> {
        return flow {
            try {
                repository.changeCountry(parameter.country, parameter.userUUID).collect { result ->
                    if (result.isSuccess)
                        emit(result)
                    else
                        emit(
                            Result.failure(
                                result.exceptionOrNull() ?: Exception("Firebase Firestore Change!")
                            )
                        )
                }
            } catch (error: IllegalStateException) {
                emit(Result.failure(error))
            }
        }
    }
}