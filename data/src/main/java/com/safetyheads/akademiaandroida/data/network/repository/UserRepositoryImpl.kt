package com.safetyheads.akademiaandroida.data.network.repository

import android.graphics.Bitmap
import android.net.Uri
import android.util.Log
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.DocumentReference
import com.google.firebase.firestore.FieldValue
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.GeoPoint
import com.google.firebase.storage.FirebaseStorage
import com.safetyheads.akademiaandroida.domain.entities.ResetPassword
import com.safetyheads.akademiaandroida.domain.entities.User
import com.safetyheads.akademiaandroida.domain.entities.firebasefirestore.Address
import com.safetyheads.akademiaandroida.domain.entities.firebasefirestore.Location
import com.safetyheads.akademiaandroida.domain.entities.firebasefirestore.Profile
import com.safetyheads.akademiaandroida.domain.repositories.UserRepository
import kotlinx.coroutines.channels.awaitClose
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.callbackFlow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.tasks.await
import java.io.ByteArrayOutputStream
import java.util.UUID


class UserRepositoryImpl : UserRepository {

    private val firebaseAuth = FirebaseAuth.getInstance()
    private val collectionReference = FirebaseFirestore.getInstance()
    private val storageReference = FirebaseStorage.getInstance()

    override suspend fun resetPassword(email: String): Flow<ResetPassword> = flow {
        firebaseAuth.sendPasswordResetEmail(email).await()

        emit(ResetPassword(true, null))
    }.catch { error ->
        emit(ResetPassword(false, error))
    }

    override suspend fun getProfileInformation(userUUID: String): Flow<Result<Profile>> = callbackFlow {
        val listener = collectionReference.collection("users").document(userUUID)
            .addSnapshotListener { snapshot, exception ->
                if (exception != null) {
                    trySend(Result.failure(exception))
                } else {
                    snapshot?.let { document ->
                        val firebaseId = document.getString("FirebaseId").orEmpty()
                        val address = Address(
                            document.getString("address.city").orEmpty(),
                            document.getString("address.country").orEmpty(),
                            document.getString("address.streetName").orEmpty(),
                            document.getString("address.streetNumber").orEmpty(),
                            document.getString("address.zipCode").orEmpty()
                        )

                        val currentLocationRef = document.get("currentLocation") as? GeoPoint
                        val currentLocation = Location(
                            currentLocationRef?.latitude ?: 0.0,
                            currentLocationRef?.longitude ?: 0.0
                        )


                        val homeLocationRef = document.get("homeLocation") as? GeoPoint
                        val homeLocation = Location(
                            homeLocationRef?.latitude ?: 0.0,
                            homeLocationRef?.longitude ?: 0.0
                        )

                        val fcmToken = document.getString("fcmToken").orEmpty()
                        val id = document.getString("id").orEmpty()
                        val firstName = document.getString("firstName").orEmpty()
                        val lastName = document.getString("lastName").orEmpty()
                        val userName = document.getString("userName").orEmpty()

                        val imageReference = document.get("image") as DocumentReference
                        imageReference.addSnapshotListener { snapshot, exception ->
                            if (exception != null) {
                                trySend(Result.failure(exception))
                            } else {
                                snapshot?.let { secondDocument ->
                                    val imageUrl = secondDocument.getString("url").orEmpty()
                                    val profile = Profile(
                                        firebaseId,
                                        address,
                                        currentLocation,
                                        fcmToken,
                                        firstName,
                                        homeLocation,
                                        id,
                                        imageUrl,
                                        lastName,
                                        userName
                                    )
                                    trySend(Result.success(profile))
                                }
                            }
                        }
                    }
                }
            }
        awaitClose { listener.remove() }
    }

    override suspend fun logIn(email: String, password: String): Flow<Result<String>> = callbackFlow {
        // temporary user login
        val email = "testalbertb@sh.pl"
        val password = "123456789"

        val listener = firebaseAuth.signInWithEmailAndPassword(email, password)
            .addOnCompleteListener { task ->
                if (task.isSuccessful) {
                    val user = firebaseAuth.currentUser
                    val userUUID = user?.uid.orEmpty()
                    trySend(Result.success(userUUID))
                } else {
                    Log.i("FirebaseConfigRepository", "Authentication failed.")
                }
            }
        awaitClose { listener.isCanceled }
    }

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

    override suspend fun createUser(fullName: String, email: String, password: String): Flow<User> {
        TODO("Not yet implemented")
    }

}