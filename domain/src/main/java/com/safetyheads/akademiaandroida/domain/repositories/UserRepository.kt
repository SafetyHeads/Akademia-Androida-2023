package com.safetyheads.akademiaandroida.domain.repositories

import android.graphics.Bitmap
import android.net.Uri
import com.safetyheads.akademiaandroida.domain.entities.ResetPassword
import com.safetyheads.akademiaandroida.domain.entities.User
import com.safetyheads.akademiaandroida.domain.entities.firebasefirestore.Image
import com.safetyheads.akademiaandroida.domain.entities.firebasefirestore.Profile
import kotlinx.coroutines.flow.Flow

interface UserRepository {
    fun createUser(fullName: String, email: String, password: String): Flow<User>
    fun resetPassword(email: String): Flow<ResetPassword>
    fun getProfileInformation(userUUID: String): Flow<Result<Profile>>
    fun logIn(email: String, password: String): Flow<Result<String>>
    fun addImageToFirebaseStorage(imageUri: Uri): Flow<Result<Image>>
    fun addImageToFirebaseStorage(imageBitmap: Bitmap): Flow<Result<Image>>
    fun addImageToFirestore(image: Image): Flow<Result<String>>
    fun addImageToFirebaseUserProfileFirestore(userUUID: String, imageStringReference: String): Flow<Result<Boolean>>
    fun removeImage(userUUID: String): Flow<Result<Boolean>>
}
