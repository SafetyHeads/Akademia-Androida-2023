package com.safetyheads.akademiaandroida.domain.usecases

import com.safetyheads.akademiaandroida.domain.repositories.UserRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow

class ChangeNameUseCase(private val repository: UserRepository) :
    BaseUseCase<ChangeNameUseCase.Param, Boolean> {

    class Param(
        val firstName: String,
        val lastName: String,
        val userUUID: String
    ) : BaseUseCase.Params

    override suspend fun invoke(parameter: Param): Flow<Result<Boolean>> {
        return flow {
            try {
                repository.changeName(parameter.firstName, parameter.lastName, parameter.userUUID)
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