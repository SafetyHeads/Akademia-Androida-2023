package com.safetyheads.akademiaandroida.usersessionmanager

import com.google.firebase.auth.FirebaseAuth
import com.safetyheads.akademiaandroida.domain.entities.Session


class SessionGenerator(private val firebaseAuth: FirebaseAuth) {

    fun generateNewSession(): Session {
        val email = firebaseAuth.currentUser?.email ?: ""
        return Session(email)
    }
}