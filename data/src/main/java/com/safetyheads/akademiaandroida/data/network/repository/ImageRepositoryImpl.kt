package com.safetyheads.akademiaandroida.data.network.repository

import com.google.firebase.firestore.FirebaseFirestore
import com.safetyheads.akademiaandroida.domain.entities.Media
import com.safetyheads.akademiaandroida.domain.repositories.ImageRepository
import kotlinx.coroutines.channels.awaitClose
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.callbackFlow

class ImageRepositoryImpl(
    private val firestoreReference: FirebaseFirestore
) : ImageRepository {

    override suspend fun getInstagramImages(): Flow<Result<ArrayList<Media>>> = callbackFlow {
        val listener = firestoreReference.collection("images")
            .whereEqualTo("type", "instagram")
            .get()
            .addOnSuccessListener { documentSnapshot ->
                val instagramImageList: ArrayList<Media> = ArrayList()

                for (document in documentSnapshot.documents) {
                    val data = document.data

                    if (data != null) {
                        val createAt = data.getOrDefault("createAt", "").toString()
                        val imageUrl = data.getOrDefault("url", "").toString()
                        instagramImageList.add(Media(
                            "empty",
                            "empty",
                            createAt,
                            imageUrl,
                            "instagram"))
                    }
                }
                trySend(Result.success(instagramImageList))
            }.addOnFailureListener { e ->
                trySend(Result.failure(e))
            }
        awaitClose { listener.isCanceled }
    }
}