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
    suspend fun changeName(firstName: String, lastName: String, userUUID: String): Flow<Result<Boolean>>
    suspend fun changeJobPosition(jobPosition: String, userUUID: String): Flow<Result<Boolean>>
    suspend fun changePhoneNumber(phoneNumber: String, userUUID: String): Flow<Result<Boolean>>
    suspend fun changeStreetAddress(streetName: String, streetNumber: String, userUUID: String): Flow<Result<Boolean>>
    suspend fun changeCityAddress(zipCode: String, city: String, userUUID: String): Flow<Result<Boolean>>
    suspend fun changeCountry(country: String, userUUID: String): Flow<Result<Boolean>>

}
