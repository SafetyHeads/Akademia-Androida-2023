package com.safetyheads.akademiaandroida.domain.usecases

import android.net.Uri
import com.safetyheads.akademiaandroida.domain.repositories.UserRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow

class AddImageToFirebaseUriStorage(private val repository: UserRepository) :
    BaseUseCase<AddImageToFirebaseUriStorage.ImageParam, String> {

    @JvmInline
    value class ImageParam(val imageUri: Uri) : BaseUseCase.Params

    override suspend fun invoke(parameter: ImageParam): Flow<Result<String>> {
        return flow {
            try {
                repository.addImageToFirebaseStorage(parameter.imageUri).collect { imageUri ->
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
