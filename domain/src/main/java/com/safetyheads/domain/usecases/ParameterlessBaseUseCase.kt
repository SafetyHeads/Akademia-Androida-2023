package com.safetyheads.domain.usecases

import com.safetyheads.data.usecases.BaseUseCase
import kotlinx.coroutines.flow.Flow


interface ParameterlessBaseUseCase<out DataType : Any>
    : BaseUseCase<ParameterlessBaseUseCase.NoParameter, DataType> {

    object NoParameter : BaseUseCase.Params

    override suspend fun invoke(parameter: NoParameter): Flow<Result<DataType>> {
        return invoke()
    }

    suspend operator fun invoke(): Flow<Result<DataType>>
}