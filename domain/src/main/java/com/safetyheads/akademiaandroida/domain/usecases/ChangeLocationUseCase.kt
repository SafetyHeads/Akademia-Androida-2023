package com.safetyheads.akademiaandroida.domain.usecases

import com.safetyheads.akademiaandroida.domain.entities.location.ChangeLocation
import com.safetyheads.akademiaandroida.domain.repositories.UserRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow

class ChangeLocationUseCase(
    private val repository: UserRepository,
    private val changeLocation: ChangeLocation,
) : BaseUseCase<ChangeLocationUseCase.Param, String> {

    class Param(
        val mapChange: Map<String, Any>,
        val functionTag: String,
    ) : BaseUseCase.Params

    override suspend fun invoke(parameter: Param): Flow<Result<String>> {
        return flow {
            try {
                if (changeLocation.sessionIsActive()) {
                    repository.changeUser(
                        parameter.mapChange,
                        parameter.functionTag,
                        changeLocation.sessionInformation().userUUID
                    ).collect { result ->
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
                } else {
                    emit(
                        Result.failure(
                            Exception("Firebase Firestore Change!")
                        )
                    )
                }
            } catch (error: IllegalStateException) {
                emit(Result.failure(error))
            }
        }
    }
}



