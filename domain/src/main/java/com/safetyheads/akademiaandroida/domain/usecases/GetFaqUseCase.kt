package com.safetyheads.akademiaandroida.domain.usecases

import com.safetyheads.akademiaandroida.domain.entities.faqs.FaqType
import com.safetyheads.akademiaandroida.domain.entities.firebasefirestore.faq.Faq
import com.safetyheads.akademiaandroida.domain.repositories.FaqRepository
import kotlinx.coroutines.flow.Flow

class GetFaqUseCase(private val repository: FaqRepository): ParameterlessBaseUseCase<MutableList<Faq>> {
    override suspend fun invoke(): Flow<Result<MutableList<Faq>>> {
        return repository.getFaq()
    }
}
