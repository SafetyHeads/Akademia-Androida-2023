package com.safetyheads.akademiaandroida.domain.usecases

import com.safetyheads.akademiaandroida.domain.repositories.UserRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow

class ProfileDeleteAccountUseCase(private val repository: UserRepository) :
    BaseUseCase<ProfileDeleteAccountUseCase.Param, String> {

    class Param(val userUUID: String) : BaseUseCase.Params

    override suspend fun invoke(parameter: Param): Flow<Result<String>> {
        return flow {
            try {
                repository.deleteAccount(parameter.userUUID).collect { result ->
                    if (result.isSuccess)
                        emit(result)
                    else
                        emit(
                            Result.failure(
                                result.exceptionOrNull() ?: Exception("Firebase Firestore Delete profile failure!")
                            )
                        )
                }
            } catch (error: IllegalStateException) {
                emit(Result.failure(error))
            }
        }
    }
}