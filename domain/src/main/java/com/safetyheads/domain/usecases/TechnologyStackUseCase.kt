package com.safetyheads.domain.usecases

import com.safetyheads.domain.entities.technologystack.ChildModel
import com.safetyheads.domain.entities.technologystack.ParentModel
import com.safetyheads.domain.entities.technologystack.TechnologyStackType
import com.safetyheads.domain.repositories.TechnologyStackRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.map

class TechnologyStackUseCase(private val technologyStackRepository: TechnologyStackRepository) :
    BaseUseCase<TechnologyStackUseCase.TechnologyStackParam, List<ParentModel>> {
    class TechnologyStackParam(val type: TechnologyStackType) : BaseUseCase.Params

    override suspend fun invoke(parameter: TechnologyStackParam): Flow<Result<List<ParentModel>>> =
        flow {
            technologyStackRepository.getTechnologyStack(parameter.type)
                .map { result -> result.getOrDefault(emptyList()) }
                .map { technologyStacks ->
                    technologyStacks.map { stack ->
                        ParentModel(stack.name, stack.details.map { detail -> ChildModel(detail) })
                    }
                }
                .catch { emit(Result.failure(it)) }
                .collect { list -> emit(Result.success(list)) }
        }
}