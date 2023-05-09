package com.safetyheads.akademiaandroida.domain.entities.firebasefirestore

data class Profile(
    val FirebaseId: String,
    val address: Address,
    val currentLocation: Location,
    val fcmToken: String,
    val firstName: String,
    val homeLocation: Location,
    val id: String,
    val image: String,
    val lastName: String,
    val userName: String
)