package com.safetyheads.akademiaandroida.domain.usecases

import android.graphics.Bitmap
import com.safetyheads.akademiaandroida.domain.entities.firebasefirestore.Image
import com.safetyheads.akademiaandroida.domain.repositories.UserRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow

class AddImageToFirebaseBitmapStorage(private val repository: UserRepository) :
    BaseUseCase<AddImageToFirebaseBitmapStorage.ProfileParam, Image> {

    @JvmInline
    value class ProfileParam(val imageBitmap: Bitmap) : BaseUseCase.Params

    override suspend fun invoke(parameter: ProfileParam): Flow<Result<Image>> {
        return flow {
            try {
                repository.addImageToFirebaseStorage(parameter.imageBitmap).collect { imageUri ->
                    if (imageUri.isSuccess)
                        emit(imageUri)
                    else
                        emit(
                            Result.failure(
                                imageUri.exceptionOrNull() ?: Exception("Add Image to Firebase Storage Error!")
                            )
                        )
                }
            } catch (error: IllegalStateException) {
                emit(Result.failure(error))
            }
        }
    }
}
