package com.safetyheads.domain.repositories

import com.safetyheads.domain.entities.ResetPassword
import kotlinx.coroutines.flow.Flow

class UserRepositoryImpl : UserRepository {
    override fun resetPassword(): Flow<ResetPassword> {
        TODO("Not yet implemented")
    }
}