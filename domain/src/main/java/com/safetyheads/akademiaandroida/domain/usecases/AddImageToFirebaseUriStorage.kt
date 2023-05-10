package com.safetyheads.akademiaandroida.domain.usecases

import android.net.Uri
import com.safetyheads.akademiaandroida.domain.entities.firebasefirestore.Image
import com.safetyheads.akademiaandroida.domain.repositories.UserRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow

class AddImageToFirebaseUriStorage(private val repository: UserRepository) :
    BaseUseCase<AddImageToFirebaseUriStorage.ProfileParam, Image> {

    @JvmInline
    value class ProfileParam(val imageUri: Uri) : BaseUseCase.Params

    override suspend fun invoke(parameter: ProfileParam): Flow<Result<Image>> {
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
