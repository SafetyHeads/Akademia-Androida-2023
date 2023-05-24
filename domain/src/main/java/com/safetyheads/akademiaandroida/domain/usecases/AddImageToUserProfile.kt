package com.safetyheads.akademiaandroida.domain.usecases

import com.safetyheads.akademiaandroida.domain.repositories.ImageRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow

class AddImageToUserProfile(private val repository: ImageRepository) :
    BaseUseCase<AddImageToUserProfile.ImageParam, String> {

    class ImageParam(val userUUID: String, val imageStringReference: String) : BaseUseCase.Params

    override suspend fun invoke(parameter: ImageParam): Flow<Result<String>> {
        return flow {
            try {
                repository.addImageToUserProfile(
                    parameter.userUUID,
                    parameter.imageStringReference
                ).collect { firestoreChange ->
                    if (firestoreChange.isSuccess)
                        emit(firestoreChange)
                    else
                        emit(
                            Result.failure(
                                firestoreChange.exceptionOrNull()
                                    ?: Exception("Add Image To Firebase User Profile Firestore Error!")
                            )
                        )
                }
            } catch (error: IllegalStateException) {
                emit(Result.failure(error))
            }
        }
    }
}
