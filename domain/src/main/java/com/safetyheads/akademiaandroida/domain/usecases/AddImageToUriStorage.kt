package com.safetyheads.akademiaandroida.domain.usecases

import com.safetyheads.akademiaandroida.domain.entities.ImageUri
import com.safetyheads.akademiaandroida.domain.repositories.ImageRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow

class AddImageToUriStorage(private val repository: ImageRepository) :
    BaseUseCase<AddImageToUriStorage.ImageParam, String> {

    @JvmInline
    value class ImageParam(val imageUri: ImageUri) : BaseUseCase.Params

    override suspend fun invoke(parameter: ImageParam): Flow<Result<String>> {
        return flow {
            try {
                repository.addImageToStorage(parameter.imageUri).collect { imageUri ->
                    if (imageUri.isSuccess)
                        emit(imageUri)
                    else
                        emit(
                            Result.failure(
                                imageUri.exceptionOrNull()
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
