package com.safetyheads.akademiaandroida.domain.repositories

import android.graphics.Bitmap
import android.net.Uri
import com.safetyheads.akademiaandroida.domain.entities.ResetPassword
import com.safetyheads.akademiaandroida.domain.entities.User
import com.safetyheads.akademiaandroida.domain.entities.firebasefirestore.Profile
import kotlinx.coroutines.flow.Flow

interface UserRepository {
    suspend fun createUser(fullName: String, email: String, password: String): Flow<User>
    suspend fun resetPassword(email: String): Flow<ResetPassword>
    suspend fun getProfileInformation(userUUID: String): Flow<Result<Profile>>
    suspend fun logIn(email: String, password: String): Flow<Result<String>>
    suspend fun addImageToFirebaseStorage(imageUri: Uri): Flow<Result<String>>
    suspend fun addImageToFirebaseStorage(imageBitmap: Bitmap): Flow<Result<String>>
    suspend fun addImageToFirebaseUserProfileFirestore(userUUID: String, imageStringReference: String): Flow<Result<String>>
    suspend fun removeImageFromUserProfileFirestore(userUUID: String): Flow<Result<String>>
    suspend fun removeImageFromFirebaseStorage(imageStringReference: String): Flow<Result<Boolean>>
}
