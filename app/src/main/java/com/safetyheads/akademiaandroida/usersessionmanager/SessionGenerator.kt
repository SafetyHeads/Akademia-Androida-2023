package com.safetyheads.akademiaandroida.usersessionmanager

import com.google.firebase.auth.FirebaseAuth
import com.safetyheads.akademiaandroida.domain.entities.Session


class SessionGenerator(private val firebaseAuth: FirebaseAuth) {

    fun generateNewSession(): Session {
        val email = firebaseAuth.currentUser?.email ?: ""
        val userUUID = firebaseAuth.currentUser?.uid ?: ""
        return Session(email, userUUID)
    }
}