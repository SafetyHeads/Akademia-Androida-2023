package com.safetyheads.akademiaandroida.domain.usecases

import com.safetyheads.akademiaandroida.domain.entities.RawBitmap
import com.safetyheads.akademiaandroida.domain.repositories.ImageRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow

class AddImageToBitmapStorage(private val repository: ImageRepository) :
    BaseUseCase<AddImageToBitmapStorage.ImageParam, String> {

    @JvmInline
    value class ImageParam(val imageBitmap: RawBitmap) : BaseUseCase.Params

    override suspend fun invoke(parameter: ImageParam): Flow<Result<String>> {
        return flow {
            try {
                repository.addImageToStorage(parameter.imageBitmap).collect { imageUri ->
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
