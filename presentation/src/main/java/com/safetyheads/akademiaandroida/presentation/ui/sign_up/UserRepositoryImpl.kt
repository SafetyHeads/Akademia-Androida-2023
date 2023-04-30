package com.safetyheads.akademiaandroida.presentation.ui.sign_up

import com.google.firebase.auth.FirebaseAuth
import com.safetyheads.akademiaandroida.domain.entities.ResetPassword
import com.safetyheads.akademiaandroida.domain.entities.User
import com.safetyheads.akademiaandroida.domain.repositories.UserRepository
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