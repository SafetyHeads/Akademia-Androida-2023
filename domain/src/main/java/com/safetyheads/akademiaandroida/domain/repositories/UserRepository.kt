package com.safetyheads.akademiaandroida.domain.repositories

import com.safetyheads.akademiaandroida.domain.entities.ResetPassword
import com.safetyheads.akademiaandroida.domain.entities.User
import kotlinx.coroutines.flow.Flow

interface UserRepository {
    fun createUser(fullName: String, email: String, password: String): Flow<com.safetyheads.akademiaandroida.domain.entities.User>
    fun resetPassword(email: String): Flow<com.safetyheads.akademiaandroida.domain.entities.ResetPassword>
}
