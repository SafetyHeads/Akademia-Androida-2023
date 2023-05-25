package com.safetyheads.akademiaandroida.domain.repositories

import com.safetyheads.akademiaandroida.domain.entities.ResetPassword
import com.safetyheads.akademiaandroida.domain.entities.User
import com.safetyheads.akademiaandroida.domain.entities.firebasefirestore.Profile
import kotlinx.coroutines.flow.Flow

interface UserRepository {
    fun createUser(fullName: String, email: String, password: String): Flow<User>
    fun resetPassword(email: String): Flow<ResetPassword>
    fun getProfileInformation(userUUID: String): Flow<Result<Profile>>
    fun logIn(email: String, password: String): Flow<Result<String>>
    suspend fun logOut(): Flow<Result<Boolean>>
    suspend fun deleteAccount(): Flow<Result<Boolean>>
    suspend fun changeUser(mapChange: Map<String, Any>, functionTag: String, userUUID: String): Flow<Result<String>>

}
