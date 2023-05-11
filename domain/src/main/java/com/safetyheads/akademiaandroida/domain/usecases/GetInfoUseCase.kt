package com.safetyheads.akademiaandroida.domain.usecases

import com.safetyheads.akademiaandroida.domain.entities.firebasefirestore.Info
import com.safetyheads.akademiaandroida.domain.repositories.CompanyInfoRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.map

class GetInfoUseCase(private val repository: CompanyInfoRepository) :
    ParameterlessBaseUseCase<Info> {

    override suspend fun invoke(): Flow<Result<Info>> {
        return repository.getInfo()
            .map { result -> result }
            .catch { exception -> emit(Result.failure(exception)) }
    }
}