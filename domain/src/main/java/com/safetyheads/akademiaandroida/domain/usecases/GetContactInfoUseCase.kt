package com.safetyheads.akademiaandroida.domain.usecases

import com.safetyheads.akademiaandroida.domain.entities.firebasefirestore.ContactInfo
import com.safetyheads.akademiaandroida.domain.repositories.CompanyInfoRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.map

class GetContactInfoUseCase(private val repository: CompanyInfoRepository) :
    ParameterlessBaseUseCase<ContactInfo> {

    override suspend fun invoke(): Flow<Result<ContactInfo>> {
        return repository.getContactInfo()
            .map { result -> result }
            .catch { exception -> emit(Result.failure(exception)) }
    }
}
