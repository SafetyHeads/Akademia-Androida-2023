package com.safetyheads.akademiaandroida.domain.usecases

import android.net.Uri
import com.safetyheads.akademiaandroida.domain.repositories.UserRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow

class RemoveImageFromUserProfileFirestore(private val repository: UserRepository) :
    BaseUseCase<RemoveImageFromUserProfileFirestore.ImageParam, String> {

    @JvmInline
    value class ImageParam(val userUUID: String) : BaseUseCase.Params

    override suspend fun invoke(parameter: ImageParam): Flow<Result<String>> {
        return flow {
            try {
                repository.removeImageFromUserProfileFirestore(parameter.userUUID).collect { imageStringReference ->
                    if (imageStringReference.isSuccess)
                        emit(imageStringReference)
                    else
                        emit(
                            Result.failure(
                                imageStringReference.exceptionOrNull() ?: Exception("Add Image to Firebase Storage Error!")
                            )
                        )
                }
            } catch (error: IllegalStateException) {
                emit(Result.failure(error))
            }
        }
    }
}
