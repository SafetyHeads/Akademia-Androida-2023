package com.safetyheads.akademiaandroida.domain.usecases

import com.safetyheads.akademiaandroida.domain.repositories.UserRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow

class RemoveImageToFirestoreFirebaseStorage(private val repository: UserRepository) :
    BaseUseCase<RemoveImageToFirestoreFirebaseStorage.ProfileParam, Boolean> {

    @JvmInline
    value class ProfileParam(val userUUID: String) : BaseUseCase.Params

    override suspend fun invoke(parameter: ProfileParam): Flow<Result<Boolean>> {
        return flow {
            try {
                repository.removeImage(parameter.userUUID).collect { remove ->
                    if (remove.isSuccess)
                        emit(remove)
                    else
                        emit(
                            Result.failure(
                                remove.exceptionOrNull() ?: Exception("Remove Image Error!")
                            )
                        )
                }
            } catch (error: IllegalStateException) {
                emit(Result.failure(error))
            }
        }
    }
}
