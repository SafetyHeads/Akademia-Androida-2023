package com.safetyheads.domain.repositories

import com.safetyheads.domain.entities.ResetPassword
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow

class UserRepositoryImpl
    : UserRepository {
    override fun resetPassword(): Flow<ResetPassword> {
        val test = flow {
            emit(ResetPassword("test"))
        }
//        Firebase
        return test
    }
}