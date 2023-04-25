package com.safetyheads.domain.usecases

import com.safetyheads.domain.entities.firebasefirestore.Address
import com.safetyheads.domain.repositories.CompanyInfoRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.map

class GetAddressUseCase(private val repository: CompanyInfoRepository) :
    ParameterlessBaseUseCase<Address> {

    override suspend fun invoke(): Flow<Result<Address>> {
        return repository.getAddress()
            .map { result -> result }
            .catch { exception -> emit(Result.failure(exception)) }
    }
}