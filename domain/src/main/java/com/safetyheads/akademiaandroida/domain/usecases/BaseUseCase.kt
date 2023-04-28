package com.safetyheads.akademiaandroida.domain.usecases

import kotlinx.coroutines.flow.Flow

interface BaseUseCase<in Parameter: com.safetyheads.akademiaandroida.domain.usecases.BaseUseCase.Params, out DataType: Any> {

    interface Params

    suspend operator fun invoke(parameter: Parameter): Flow<Result<DataType>>
}
