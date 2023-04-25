package com.safetyheads.akademiaandroida.presentation.ui.sign_up

import com.safetyheads.domain.entities.ResetPassword
import com.safetyheads.domain.entities.User
import com.safetyheads.domain.repositories.UserRepository
import kotlinx.coroutines.flow.Flow

class UserRepositoryImpl : UserRepository {
    override fun createUser(fullName: String, email: String, password: String): Flow<User> {
        TODO("Not yet implemented")
    }

    override fun resetPassword(email: String): Flow<ResetPassword> {
        TODO("Not yet implemented")
    }

}