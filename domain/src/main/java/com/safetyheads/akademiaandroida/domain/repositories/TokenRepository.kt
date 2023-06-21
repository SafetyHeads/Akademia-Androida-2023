package com.safetyheads.akademiaandroida.domain.repositories

import kotlinx.coroutines.flow.Flow

interface TokenRepository{
    fun getMessagingToken(): Flow<String>
}