package com.safetyheads.akademiaandroida.domain.usecases

import com.safetyheads.akademiaandroida.domain.repositories.UserRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow

class AddImageToFirebaseUserProfileFirestore(private val repository: UserRepository) :
    BaseUseCase<AddImageToFirebaseUserProfileFirestore.ImageParam, String> {

    class ImageParam(val userUUID: String, val imageStringReference: String) : BaseUseCase.Params

    override suspend fun invoke(parameter: ImageParam): Flow<Result<String>> {
        return flow {
            try {
                repository.addImageToFirebaseUserProfileFirestore(
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
