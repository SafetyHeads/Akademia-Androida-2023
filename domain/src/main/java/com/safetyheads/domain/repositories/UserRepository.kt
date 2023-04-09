package com.safetyheads.domain.repositories

import com.safetyheads.domain.entities.ResetPassword
import kotlinx.coroutines.flow.Flow

interface UserRepository {
    fun resetPassword(): Flow<ResetPassword>
}