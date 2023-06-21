package com.safetyheads.akademiaandroida.presentation.services

import android.location.Location
import kotlinx.coroutines.flow.Flow

interface LocationClient {
    fun getLocationUpdates(interval: Long): Flow<Location>
    @SuppressWarnings("unused")
    class LocationException(message: String): Exception()
}