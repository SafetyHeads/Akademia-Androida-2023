package com.safetyheads.akademiaandroida.domain.usecases

import com.safetyheads.akademiaandroida.domain.repositories.UserRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow

class ChangeLocationUseCase(
    private val userRepository: UserRepository,
    private val isLoggedInUseCase: IsLoggedInUseCase,
    private val getSessionUseCase: GetSessionUseCase,
) : BaseUseCase<ChangeLocationUseCase.Param, Boolean> {

    class Param(
        val mapChange: Map<String, Any>
    ) : BaseUseCase.Params

    override suspend fun invoke(parameter: Param): Flow<Result<Boolean>> {
        return flow {
            try {
                isLoggedInUseCase.invoke().collect { isLogged ->
                    if (isLogged.isSuccess && isLogged.getOrNull() == true) {
                        getSessionUseCase.invoke().collect { session ->
                            if (session.isSuccess && session.getOrNull()?.userUUID?.isNotEmpty() == true) {
                                userRepository.changeUser(
                                    parameter.mapChange,
                                    "LocationChange",
                                    session.getOrNull()?.userUUID.orEmpty()
                                ).collect { changeLocationResult ->
                                    if (changeLocationResult.isSuccess)
                                        emit(Result.success(true))
                                    else
                                        emit(Result.success(false))
                                }
                            } else {
                                emit(Result.failure(Exception("Session closed!")))
                            }
                        }
                    } else {
                        emit(Result.failure(Exception("User is Unlogged!")))
                    }
                }
            } catch (error: IllegalStateException) {
                emit(Result.failure(error))
            }
        }
    }
}



