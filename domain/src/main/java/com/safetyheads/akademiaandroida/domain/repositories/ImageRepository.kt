package com.safetyheads.akademiaandroida.domain.repositories

import com.safetyheads.akademiaandroida.domain.entities.ImageUri
import com.safetyheads.akademiaandroida.domain.entities.RawBitmap
import kotlinx.coroutines.flow.Flow

interface ImageRepository {
    suspend fun addImageToStorage(imageUri: ImageUri): Flow<Result<String>>
    suspend fun addImageToStorage(imageBitmap: RawBitmap): Flow<Result<String>>
    suspend fun addImageToUserProfile(userUUID: String, imageStringReference: String): Flow<Result<String>>
    suspend fun removeImageFromUserProfile(userUUID: String): Flow<Result<String>>
    suspend fun removeImageFromStorage(imageStringReference: String): Flow<Result<Boolean>>
}