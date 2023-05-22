package com.safetyheads.akademiaandroida.domain.usecases

import com.safetyheads.akademiaandroida.domain.repositories.ImageRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow

class RemoveImageFromFirebaseStorage(private val repository: ImageRepository) :
    BaseUseCase<RemoveImageFromFirebaseStorage.ImageParam, Boolean> {

    @JvmInline
    value class ImageParam(val imageStringReference: String) : BaseUseCase.Params

    override suspend fun invoke(parameter: ImageParam): Flow<Result<Boolean>> {
        return flow {
            try {
                repository.removeImageFromFirebaseStorage(parameter.imageStringReference)
                    .collect { firestoreChange ->
                        if (firestoreChange.isSuccess)
                            emit(firestoreChange)
                        else
                            emit(
                                Result.failure(
                                    firestoreChange.exceptionOrNull()
                                        ?: Exception("Add Image to Firebase Storage Error!")
                                )
                            )
                    }
            } catch (error: IllegalStateException) {
                emit(Result.failure(error))
            }
        }
    }
}
