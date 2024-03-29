package com.safetyheads.akademiaandroida.data.network.repository

import android.content.ContentValues.TAG
import android.util.Log
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseAuthException
import com.google.firebase.auth.FirebaseAuthRecentLoginRequiredException
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
import com.safetyheads.akademiaandroida.domain.repositories.UserSessionManager
import kotlinx.coroutines.channels.awaitClose
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.callbackFlow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.tasks.await

class UserRepositoryImpl(
    private val sessionManager: UserSessionManager,
    private val firebaseAuth: FirebaseAuth,
    private val collectionReference: FirebaseFirestore
) : UserRepository {

    override suspend fun resetPassword(email: String): Flow<ResetPassword> = flow {
        firebaseAuth.sendPasswordResetEmail(email).await()

        emit(ResetPassword(true, null))
    }.catch { error ->
        emit(ResetPassword(false, error))
    }

    override suspend fun changePassword(oldPassword: String, newPassword: String): Flow<Result<Unit>> = flow {
        try {
            firebaseAuth.currentUser?.updatePassword(newPassword)?.await()
            emit(Result.success(Unit))
        } catch (e: FirebaseAuthRecentLoginRequiredException) {
            firebaseAuth.currentUser?.email?.let { email ->
                logIn(email, oldPassword).collect { result ->
                    if (result.isSuccess) {
                        changePassword(oldPassword, newPassword).collect {
                            emit(it)
                        }
                    } else {
                        emit(Result.failure(e))
                    }
                }

            } ?: emit(Result.failure(Exception("User is null")))

        } catch (e: Exception) {
            emit(Result.failure(e))
        }
    }

    override suspend fun updateFcmToken(userUUID: String, token: String): Flow<Result<Unit>> = flow {
        try {
            FirebaseFirestore.getInstance().collection("users").document(userUUID).update(
                mapOf("fcmToken" to token)
            ).await()
            emit(Result.success(Unit))
        } catch (e: Exception) {
            emit(Result.failure(e))
        }
    }

    override suspend fun anonymousLogIn(): Flow<Result<String>> = callbackFlow {
        val listener = firebaseAuth.signInAnonymously()
            .addOnCompleteListener { anonymousLogInResult ->
                if (anonymousLogInResult.isSuccessful) {
                    val user = firebaseAuth.currentUser
                    trySend(Result.success(user.toString()))
                }
            }
            .addOnFailureListener { error ->
                trySend(Result.failure(error))
            }
        awaitClose { listener.isCanceled }
    }

    override suspend fun getProfileInformation(userUUID: String): Flow<Result<Profile>> = callbackFlow {
        val collectionReference = FirebaseFirestore.getInstance()
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
                        val email = document.getString("email").orEmpty()
                        val jobPosition = document.getString("jobPosition").orEmpty()
                        val phoneNumber = document.getString("phoneNumber").orEmpty()
                        val imageReference = document.get("image") as DocumentReference
                        imageReference.addSnapshotListener { snapshot, exception ->
                            if (exception != null) {
                                trySend(Result.failure(exception))
                            } else {
                                snapshot?.let { secondDocument ->
                                    val imageUrl = secondDocument.getString("url").orEmpty()
                                    val profile = Profile(
                                        firebaseId, email,
                                        address, currentLocation,
                                        fcmToken, firstName,
                                        homeLocation, id,
                                        imageUrl, lastName,
                                        userName, phoneNumber,
                                        jobPosition
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
        val listener = firebaseAuth.signInWithEmailAndPassword(email, password)
            .addOnCompleteListener { task ->
                if (task.isSuccessful) {
                    val firestore = FirebaseFirestore.getInstance()
                    val usersCollection = firestore.collection("users")
                    val user = firebaseAuth.currentUser
                    val userUUID = user?.uid.orEmpty()
                    changeSession()
                    trySend(Result.success(userUUID))

                    val docRef = usersCollection.document(userUUID)
                    val userDocumentReference = firestore.collection("users").document(userUUID)

                    docRef.get().addOnSuccessListener { document ->
                        if (document != null && document.exists()) {
                            Log.d(TAG, "DocumentSnapshot data: ${document.data}")
                            val firebaseId = document.getString("FirebaseId").orEmpty()
                            if (firebaseId == userUUID) {
                                Log.d(TAG, "Profile exists with FirebaseId: $firebaseId")
                            } else {
                                Log.d(TAG, "No matching FirebaseId found")
                            }
                        } else {
                            userDocumentReference.get().addOnSuccessListener { document ->
                                if (document != null) {
                                    val firebaseId = document.getString("FirebaseId").orEmpty()
                                    if (firebaseId.isEmpty()) {
                                        val userData = hashMapOf(
                                            "FirebaseId" to userUUID,
                                            // można tu inicjalizować pozostałe pola profilu
                                        )
                                        userDocumentReference.set(userData).addOnSuccessListener {
                                            Log.d("Firestore", "DocumentSnapshot successfully written!")
                                        }
                                            .addOnFailureListener { e ->
                                                Log.w("Firestore", "Error writing document", e)
                                            }
                                    }
                                } else {
                                    Log.i("FirebaseConfigRepository", "Authentication failed.")
                                }
                            }
                        }
                    }
                        .addOnFailureListener { exception ->
                            Log.d(TAG, "get failed with ", exception)
                        }
                } else
                    trySend(Result.failure(task.exception ?: Exception("failed")))
            }
        awaitClose { listener.isCanceled }
    }

    override suspend fun logOut(): Flow<Result<Boolean>> = flow {
        if (firebaseAuth.currentUser != null) {
            firebaseAuth.signOut()
            changeSession()
            emit(Result.success(true))
        } else {
            emit(Result.failure(Exception("User Logout failed!")))
        }
    }

    override suspend fun deleteAccount(userUUID: String): Flow<Result<String>> = callbackFlow {
        val user = firebaseAuth.currentUser

        if (user == null) {
            trySend(Result.failure(Exception("User is not authenticated.")))
            close()
            return@callbackFlow
        }

        val userDocSnapshot = collectionReference.collection("users").document(userUUID).get().await()
        val previousImageRef = userDocSnapshot.get("image") as DocumentReference
        val previousImageStringRef = previousImageRef.get().await().id

        val listener = user.delete()
            .addOnCompleteListener { task ->
                if (task.isSuccessful) {
                    trySend(Result.success(previousImageStringRef))
                } else {
                    trySend(Result.failure(task.exception ?: Exception("Account deletion failed.")))
                }
                close()
            }

        awaitClose { listener.isCanceled }
    }

    override suspend fun changeUser(
        mapChange: Map<String, Any>,
        functionTag: String,
        userUUID: String
    ): Flow<Result<String>> = callbackFlow {
        try {
            val listener = collectionReference.collection("users").document(userUUID)
                .update(mapChange)
                .addOnCompleteListener {
                    trySend(Result.success(functionTag))
                }.addOnFailureListener { e ->
                    trySend(Result.failure(e))
                }
            awaitClose { listener.isCanceled }
        } catch (e: Exception) {
            trySend(Result.failure(e))
        }
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

    private fun changeSession() {
        when (sessionManager) {
            is UserSessionManager.Unlogged -> (sessionManager as UserSessionManager.Unlogged).logIn()
            is UserSessionManager.Logged -> (sessionManager as UserSessionManager.Logged).logOff()
        }
    }
}