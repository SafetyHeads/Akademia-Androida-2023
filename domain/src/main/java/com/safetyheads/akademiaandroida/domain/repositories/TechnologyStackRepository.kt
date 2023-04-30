package com.safetyheads.akademiaandroida.domain.repositories

import com.safetyheads.akademiaandroida.domain.entities.firebasefirestore.TechnologyStack
import kotlinx.coroutines.flow.Flow

interface TechnologyStackRepository {
    fun getTechnologyStack(): Flow<Result<TechnologyStack>>
}