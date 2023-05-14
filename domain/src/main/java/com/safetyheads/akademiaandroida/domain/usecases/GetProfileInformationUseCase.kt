package com.safetyheads.akademiaandroida.domain.usecases

import com.safetyheads.akademiaandroida.domain.entities.firebasefirestore.Profile
import com.safetyheads.akademiaandroida.domain.repositories.UserRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow

class GetProfileInformationUseCase(private val repository: UserRepository) :
    BaseUseCase<GetProfileInformationUseCase.ProfileParam, Profile> {

    @JvmInline
    value class ProfileParam(val userUUID: String) : BaseUseCase.Params

    override suspend fun invoke(parameter: ProfileParam): Flow<Result<Profile>> {
        return flow {
            try {
                repository.getProfileInformation(parameter.userUUID).collect { profileInformation ->
                    if (profileInformation.isSuccess)
                        emit(profileInformation)
                    else
                        emit(
                            Result.failure(
                                profileInformation.exceptionOrNull() ?: Exception("ProfileInformationUseCase Error!")
                            )
                        )
                }
            } catch (error: IllegalStateException) {
                emit(Result.failure(error))
            }
        }
    }
}
