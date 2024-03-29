package com.safetyheads.akademiaandroida.domain.usecases

import com.safetyheads.akademiaandroida.domain.repositories.ImageRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow

class RemoveImageFromUserProfile(private val repository: ImageRepository) :
    BaseUseCase<RemoveImageFromUserProfile.ImageParam, String> {

    @JvmInline
    value class ImageParam(val userUUID: String) : BaseUseCase.Params

    override suspend fun invoke(parameter: ImageParam): Flow<Result<String>> {
        return flow {
            try {
                repository.removeImageFromUserProfile(parameter.userUUID)
                    .collect { imageStringReference ->
                        if (imageStringReference.isSuccess)
                            emit(imageStringReference)
                        else
                            emit(
                                Result.failure(
                                    imageStringReference.exceptionOrNull()
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
