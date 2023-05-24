package com.safetyheads.akademiaandroida.domain.repositories

import android.graphics.Bitmap
import android.net.Uri
import kotlinx.coroutines.flow.Flow

interface ImageRepository {
    suspend fun addImageToStorage(imageUri: Uri): Flow<Result<String>>
    suspend fun addImageToStorage(imageBitmap: Bitmap): Flow<Result<String>>
    suspend fun addImageToUserProfile(userUUID: String, imageStringReference: String): Flow<Result<String>>
    suspend fun removeImageFromUserProfile(userUUID: String): Flow<Result<String>>
    suspend fun removeImageFromStorage(imageStringReference: String): Flow<Result<Boolean>>
}