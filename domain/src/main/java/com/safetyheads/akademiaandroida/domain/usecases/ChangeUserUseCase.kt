package com.safetyheads.akademiaandroida.domain.usecases

import com.safetyheads.akademiaandroida.domain.repositories.UserRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow

class ChangeUserUseCase(private val repository: UserRepository) :
    BaseUseCase<ChangeUserUseCase.Param, String> {

    class Param(
        val mapChange: Map<String, Any>,
        val functionTag: String,
        val userUUID: String
    ) : BaseUseCase.Params

    override suspend fun invoke(parameter: Param): Flow<Result<String>> {
        return flow {
            try {
                repository.changeUser(parameter.mapChange, parameter.functionTag, parameter.userUUID)
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