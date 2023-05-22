package com.safetyheads.akademiaandroida.data.network.repository

import android.graphics.Bitmap
import android.net.Uri
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.DocumentReference
import com.google.firebase.firestore.FieldValue
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.storage.FirebaseStorage
import com.safetyheads.akademiaandroida.domain.repositories.ImageRepository
import kotlinx.coroutines.channels.awaitClose
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.callbackFlow
import kotlinx.coroutines.tasks.await
import java.io.ByteArrayOutputStream
import java.util.UUID

class ImageRepositoryImpl(
    private val collectionReference: FirebaseFirestore,
    private val storageReference: FirebaseStorage
) : ImageRepository {

    override suspend fun addImageToFirebaseUserProfileFirestore(
        userUUID: String,
        imageStringReference: String,
    ): Flow<Result<String>> = callbackFlow {
        try {
            val collectionRef = collectionReference.collection("images").document(imageStringReference)
            val userDocRef = collectionReference.collection("users").document(userUUID)
            val userDocSnapshot = userDocRef.get().await()
            val previousImageRef = userDocSnapshot.get("image") as DocumentReference
            val previousImageStringRef = previousImageRef.get().await().id

            val listener = collectionReference.collection("users").document(userUUID)
                .update(mapOf("image" to collectionRef))
                .addOnSuccessListener {
                    trySend(Result.success(previousImageStringRef))
                }
                .addOnFailureListener { e ->
                    trySend(Result.failure(e))
                }
            awaitClose { listener.isCanceled }
        } catch (e: Exception) {
            trySend(Result.failure(e))
        }
    }

    override suspend fun removeImageFromUserProfileFirestore(userUUID: String): Flow<Result<String>> = callbackFlow {
        try {
            val defaultUserDocRef = collectionReference.collection("images").document("default_user")
            val usersDocRef = collectionReference.collection("users").document(userUUID)
            val userDocSnapshot = usersDocRef.get().await()
            val previousImageRef = userDocSnapshot.get("image") as DocumentReference
            val previousImageStringRef = previousImageRef.get().await().id

            val listener = collectionReference.collection("users").document(userUUID)
                .update(mapOf("image" to defaultUserDocRef))
                .addOnSuccessListener {
                    trySend(Result.success(previousImageStringRef))
                }
                .addOnFailureListener { e ->
                    trySend(Result.failure(e))
                }
            awaitClose { listener.isCanceled }
        } catch (e: Exception) {
            trySend(Result.failure(e))
        }
    }

    override suspend fun removeImageFromFirebaseStorage(imageStringReference: String): Flow<Result<Boolean>> = callbackFlow {
        try {
            val documentRef = collectionReference.collection("images").document(imageStringReference)

            val listener = documentRef.delete().addOnSuccessListener {
                val storageRef = storageReference.reference.child("images/${imageStringReference}.jpg")
                storageRef.delete().addOnSuccessListener {
                    trySend(Result.success(true))
                }.addOnFailureListener { e ->
                    trySend(Result.failure(e))
                }
            }.addOnFailureListener { e ->
                trySend(Result.failure(e))
            }
            awaitClose { listener.isCanceled }
        } catch (e: Exception) {
            trySend(Result.failure(e))
        }
    }

    override suspend fun addImageToFirebaseStorage(imageBitmap: Bitmap): Flow<Result<String>> = callbackFlow {
        try {
            val fileName = UUID.randomUUID().toString()
            val baos = ByteArrayOutputStream()
            imageBitmap.compress(Bitmap.CompressFormat.JPEG, 100, baos)
            val imageData = baos.toByteArray()
            val imageRef = storageReference.reference.child("images/${fileName}.jpg")

            val listener = imageRef.putBytes(imageData)
                .addOnSuccessListener {
                    imageRef.downloadUrl.addOnSuccessListener { url ->
                        collectionReference.collection("images").document(fileName).set(
                            mapOf(
                                "url" to url,
                                "type" to "avatar",
                                "createAt" to FieldValue.serverTimestamp()
                            )
                        ).addOnSuccessListener {
                            trySend(Result.success(fileName))
                        }.addOnFailureListener { e ->
                            trySend(Result.failure(e))
                        }
                    }
                }
                .addOnFailureListener { e ->
                    trySend(Result.failure(e))
                }
            awaitClose { listener.isCanceled }
        } catch (e: Exception) {
            trySend(Result.failure(e))
        }
    }

    override suspend fun addImageToFirebaseStorage(imageUri: Uri): Flow<Result<String>> = callbackFlow {
        try {
            val fileName = UUID.randomUUID().toString()
            val imageRef = storageReference.reference.child("images/${fileName}.jpg")

            val listener = imageRef.putFile(imageUri)
                .addOnSuccessListener {
                    imageRef.downloadUrl.addOnSuccessListener { url ->
                        collectionReference.collection("images").document(fileName).set(
                            mapOf(
                                "url" to url,
                                "type" to "avatar",
                                "createAt" to FieldValue.serverTimestamp()
                            )
                        ).addOnSuccessListener {
                            trySend(Result.success(fileName))
                        }.addOnFailureListener { e ->
                            trySend(Result.failure(e))
                        }
                    }
                }
                .addOnFailureListener { e ->
                    trySend(Result.failure(e))
                }
            awaitClose { listener.isCanceled }
        } catch (e: Exception) {
            trySend(Result.failure(e))
        }
    }
}