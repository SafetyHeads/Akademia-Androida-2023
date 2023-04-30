package com.safetyheads.akademiaandroida.domain.usecases

import com.safetyheads.akademiaandroida.domain.entities.firebasefirestore.Social
import com.safetyheads.akademiaandroida.domain.repositories.CompanyInfoRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.map

class GetSocialUseCase(private val repository: CompanyInfoRepository) :
    ParameterlessBaseUseCase<Social> {

    override suspend fun invoke(): Flow<Result<Social>> {
        return repository.getSocial()
            .map { result -> result }
            .catch { exception -> emit(Result.failure(exception)) }
    }
}