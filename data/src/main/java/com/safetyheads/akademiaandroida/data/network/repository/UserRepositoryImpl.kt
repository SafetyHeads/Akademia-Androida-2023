package com.safetyheads.akademiaandroida.data.network.repository

import android.util.Log
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseAuthException
import com.google.firebase.auth.UserProfileChangeRequest
import com.google.firebase.firestore.DocumentReference
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.GeoPoint
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


class UserRepositoryImpl(
    private val firebaseAuth: FirebaseAuth,
    private val  collectionReference: FirebaseFirestore
) : UserRepository {

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



    override suspend fun createUser(fullName: String, email: String, password: String): Flow<User> = flow {
        val authResult = firebaseAuth.createUserWithEmailAndPassword(email, password).await()

        val firebaseUser = authResult.user!!

        val profileUpdates = UserProfileChangeRequest.Builder()
            .setDisplayName(fullName)
            .build()

        firebaseUser.updateProfile(profileUpdates).await()

        emit(User(firebaseUser.uid, fullName, email))
    }.catch { error ->
        if (error is FirebaseAuthException) {
            throw IllegalStateException(error.message ?: "Error during registration")
        } else {
            throw error
        }
    }

}