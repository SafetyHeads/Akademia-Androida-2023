package com.safetyheads.akademiaandroida.data

import com.google.firebase.auth.FirebaseAuth
import com.safetyheads.domain.entities.ResetPassword
import com.safetyheads.domain.entities.User
import com.safetyheads.domain.repositories.UserRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.tasks.await

class UserRepositoryImpl : UserRepository {
    private val firebaseAuth = FirebaseAuth.getInstance()

    override fun resetPassword(email: String): Flow<ResetPassword> = flow {
        firebaseAuth.sendPasswordResetEmail(email).await()
        emit(ResetPassword(true, null))
    }.catch { error ->
        emit(ResetPassword(false, error))
    }

    override fun createUser(fullName: String, email: String, password: String): Flow<User> {
        TODO("Not yet implemented")
    }
}
