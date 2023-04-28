package com.safetyheads.akademiaandroida.data

import com.google.firebase.auth.FirebaseAuth
import com.safetyheads.akademiaandroida.domain.entities.ResetPassword
import com.safetyheads.akademiaandroida.domain.entities.User
import com.safetyheads.akademiaandroida.domain.repositories.UserRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.tasks.await

class UserRepositoryImpl : com.safetyheads.akademiaandroida.domain.repositories.UserRepository {
    private val firebaseAuth = FirebaseAuth.getInstance()

    override fun resetPassword(email: String): Flow<com.safetyheads.akademiaandroida.domain.entities.ResetPassword> = flow {
        firebaseAuth.sendPasswordResetEmail(email).await()
        emit(com.safetyheads.akademiaandroida.domain.entities.ResetPassword(true, null))
    }.catch { error ->
        emit(com.safetyheads.akademiaandroida.domain.entities.ResetPassword(false, error))
    }

    override fun createUser(fullName: String, email: String, password: String): Flow<com.safetyheads.akademiaandroida.domain.entities.User> {
        TODO("Not yet implemented")
    }
}
