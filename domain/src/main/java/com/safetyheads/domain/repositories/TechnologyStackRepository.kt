package com.safetyheads.domain.repositories

import com.safetyheads.domain.entities.firebasefirestore.TechnologyStack
import kotlinx.coroutines.flow.Flow

interface TechnologyStackRepository {
    fun getTechnologyStack(): Flow<Result<TechnologyStack>>
}