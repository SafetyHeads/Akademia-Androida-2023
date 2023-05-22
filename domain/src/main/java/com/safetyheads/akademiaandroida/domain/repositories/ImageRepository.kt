package com.safetyheads.akademiaandroida.domain.repositories

import android.graphics.Bitmap
import android.net.Uri
import kotlinx.coroutines.flow.Flow

interface ImageRepository {
    suspend fun addImageToFirebaseStorage(imageUri: Uri): Flow<Result<String>>
    suspend fun addImageToFirebaseStorage(imageBitmap: Bitmap): Flow<Result<String>>
    suspend fun addImageToFirebaseUserProfileFirestore(userUUID: String, imageStringReference: String): Flow<Result<String>>
    suspend fun removeImageFromUserProfileFirestore(userUUID: String): Flow<Result<String>>
    suspend fun removeImageFromFirebaseStorage(imageStringReference: String): Flow<Result<Boolean>>
}