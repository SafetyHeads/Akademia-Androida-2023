package com.safetyheads.akademiaandroida.domain.repositories

import com.safetyheads.akademiaandroida.domain.entities.Session

interface UserSessionManager {
    val isLoggedIn: Boolean

    interface Logged {
        val session: Session
        fun logOff()
    }

    interface Unlogged {
        fun logIn()
    }
}
