package com.safetyheads.akademiaandroida.domain.usecases

import com.safetyheads.akademiaandroida.domain.entities.technologystack.ChildModel
import com.safetyheads.akademiaandroida.domain.entities.technologystack.ParentModel
import com.safetyheads.akademiaandroida.domain.entities.technologystack.TechnologyStackType
import com.safetyheads.akademiaandroida.domain.repositories.TechnologyStackRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.map

class GetTechnologyStackUseCase(private val technologyStackRepository: TechnologyStackRepository) :
    BaseUseCase<GetTechnologyStackUseCase.TechnologyStackParam, List<ParentModel>> {
    class TechnologyStackParam(val type: TechnologyStackType) : BaseUseCase.Params

    override suspend fun invoke(parameter: TechnologyStackParam): Flow<Result<List<ParentModel>>> =
        flow {
            technologyStackRepository.getTechnologyStack()
                .map { result -> result.getOrThrow() }
                .map { technologyStacks ->
                    technologyStacks.categories
                        .find { it.categoryName == parameter.type.name }
                        ?.let { stack ->
                            stack.subCategories.map { category ->
                                ParentModel(category.name, category.items.map { detail -> ChildModel(detail) })
                            }
                        } ?: error("Can't find technology category")
                }
                .catch { emit(Result.failure(it)) }
                .collect { list -> emit(Result.success(list)) }
        }
}