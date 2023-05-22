package com.safetyheads.akademiaandroida.domain.usecases

import android.graphics.Bitmap
import com.safetyheads.akademiaandroida.domain.repositories.ImageRepository
import com.safetyheads.akademiaandroida.domain.repositories.UserRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow

class AddImageToFirebaseBitmapStorage(private val repository: ImageRepository) :
    BaseUseCase<AddImageToFirebaseBitmapStorage.ImageParam, String> {

    @JvmInline
    value class ImageParam(val imageBitmap: Bitmap) : BaseUseCase.Params

    override suspend fun invoke(parameter: ImageParam): Flow<Result<String>> {
        return flow {
            try {
                repository.addImageToFirebaseStorage(parameter.imageBitmap).collect { imageUri ->
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
