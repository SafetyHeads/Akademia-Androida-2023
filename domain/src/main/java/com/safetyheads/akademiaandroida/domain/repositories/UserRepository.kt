package com.safetyheads.akademiaandroida.domain.repositories

import com.safetyheads.akademiaandroida.domain.entities.ResetPassword
import com.safetyheads.akademiaandroida.domain.entities.User
import com.safetyheads.akademiaandroida.domain.entities.firebasefirestore.Profile
import kotlinx.coroutines.flow.Flow

interface UserRepository {
    suspend fun createUser(fullName: String, email: String, password: String): Flow<User>
    suspend fun resetPassword(email: String): Flow<ResetPassword>
    suspend fun getProfileInformation(userUUID: String): Flow<Result<Profile>>
    suspend fun logIn(email: String, password: String): Flow<Result<String>>
    suspend fun logOut(): Flow<Result<Boolean>>
    suspend fun deleteAccount(userUUID: String): Flow<Result<String>>
    suspend fun changeUser(mapChange: Map<String, Any>, functionTag: String, userUUID: String): Flow<Result<String>>
    suspend fun updateFcmToken(userUUID: String, token: String): Flow<Result<Unit>>
    suspend fun anonymousLogIn(): Flow<Result<String>>
    suspend fun changePassword(oldPassword: String, newPassword: String): Flow<Result<Unit>>

}