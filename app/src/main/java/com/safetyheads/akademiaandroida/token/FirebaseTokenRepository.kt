package com.safetyheads.akademiaandroida.token

import com.google.firebase.messaging.FirebaseMessaging
import com.safetyheads.akademiaandroida.domain.repositories.TokenRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.tasks.await

class FirebaseTokenRepository: TokenRepository {
    override fun getMessagingToken(): Flow<String> = flow {
        val token = FirebaseMessaging.getInstance().token.await()
        emit(token)
    }
}