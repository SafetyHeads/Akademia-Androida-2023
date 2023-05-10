package com.safetyheads.akademiaandroida.domain.usecases

import com.safetyheads.akademiaandroida.domain.entities.firebasefirestore.Image
import com.safetyheads.akademiaandroida.domain.repositories.UserRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow

class AddImageToFirestoreStorage(private val repository: UserRepository) :
    BaseUseCase<AddImageToFirestoreStorage.ProfileParam, String> {

    @JvmInline
    value class ProfileParam(val image: Image) : BaseUseCase.Params

    override suspend fun invoke(parameter: ProfileParam): Flow<Result<String>> {
        return flow {
            try {
                repository.addImageToFirestore(parameter.image).collect { imagesPath ->
                    if (imagesPath.isSuccess)
                        emit(imagesPath)
                    else
                        emit(
                            Result.failure(
                                imagesPath.exceptionOrNull() ?: Exception("Add Image to Firebase Storage Error!")
                            )
                        )
                }
            } catch (error: IllegalStateException) {
                emit(Result.failure(error))
            }
        }
    }
}
