package com.safetyheads.domain.repositories

import com.safetyheads.domain.entities.ResetPassword
import com.safetyheads.domain.entities.User
import kotlinx.coroutines.flow.Flow

interface UserRepository {
    fun createUser(fullName: String, email: String, password: String): Flow<User>
    fun resetPassword(email: String): Flow<ResetPassword>
}
