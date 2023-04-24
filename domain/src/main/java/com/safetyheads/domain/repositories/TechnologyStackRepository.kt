package com.safetyheads.domain.repositories

import com.safetyheads.domain.entities.technologystack.TechnologyStack
import com.safetyheads.domain.entities.technologystack.TechnologyStackType
import kotlinx.coroutines.flow.Flow

interface TechnologyStackRepository {
    fun getTechnologyStack(type: TechnologyStackType): Flow<Result<List<TechnologyStack>>>

}