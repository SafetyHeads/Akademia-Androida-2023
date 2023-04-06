package com.safetyheads.akademiaandroida.sign_up

import kotlinx.coroutines.flow.Flow

interface UserRepository {
    suspend fun getUser() : Flow<User>
}