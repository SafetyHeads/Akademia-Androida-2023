package com.safetyheads.akademiaandroida.domain.usecases

import com.safetyheads.akademiaandroida.domain.repositories.UserRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow

class ProfileLogOutUseCase(private val repository: UserRepository) :
    BaseUseCase<ProfileLogOutUseCase.Param, Boolean> {

    class Param : BaseUseCase.Params

    override suspend fun invoke(parameter: Param): Flow<Result<Boolean>> {
        return flow {
            try {
                repository.logOut().collect { result ->
                    if (result.isSuccess)
                        emit(result)
                    else
                        emit(
                            Result.failure(
                                result.exceptionOrNull() ?: Exception("Firebase Firestore Logout failure!")
                            )
                        )
                }
            } catch (error: IllegalStateException) {
                emit(Result.failure(error))
            }
        }
    }
}