package com.safetyheads.akademiaandroida.domain.entities.location

import com.safetyheads.akademiaandroida.domain.entities.Session

interface ChangeLocation {
    fun sessionIsActive(): Boolean
    fun sessionInformation(): Session
}