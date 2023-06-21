package com.safetyheads.akademiaandroida.presentation.services

import android.app.Notification
import android.app.Notification.PRIORITY_MIN
import android.app.NotificationChannel
import android.app.NotificationManager
import android.app.Service
import android.content.Context
import android.content.Intent
import android.graphics.Color
import android.os.Build
import android.os.IBinder
import android.util.Log
import androidx.annotation.RequiresApi
import androidx.core.app.NotificationCompat
import com.google.android.gms.location.LocationServices
import com.google.firebase.firestore.GeoPoint
import com.safetyheads.akademiaandroida.domain.entities.LocationType
import com.safetyheads.akademiaandroida.domain.usecases.ChangeLocationUseCase
import com.safetyheads.akademiaandroida.presentation.R
import com.safetyheads.akademiaandroida.presentation.services.location.LocationObjects
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.SupervisorJob
import kotlinx.coroutines.cancel
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import org.koin.android.ext.android.inject

class LocationForegroundService : Service() {

    private val serviceScope = CoroutineScope(SupervisorJob() + Dispatchers.IO)

    private lateinit var locationForegroundClient: LocationClient
    private val changeLocationUseCase: ChangeLocationUseCase by inject()

    override fun onBind(p0: Intent?): IBinder? {
        return null
    }

    override fun onCreate() {
        super.onCreate()
        locationForegroundClient = DefaultLocationClient(
            applicationContext,
            LocationServices.getFusedLocationProviderClient(applicationContext),
            LocationType.Foreground
        )
    }

    override fun onStartCommand(intent: Intent?, flags: Int, startId: Int): Int {
        when (intent?.action) {
            ACTION_START -> start()
            ACTION_STOP -> stop()
        }
        return super.onStartCommand(intent, flags, startId)
    }

    private fun start() {
        Log.i(TAG, LocationObjects.SERVICE_FOREGROUND_START)
        val channelId =
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
                createNotificationChannel()
            } else {
                LocationObjects.EMPTY_NOTIFICATION
            }

        val notificationBuilder = NotificationCompat.Builder(this, channelId)
        val notification = notificationBuilder.setOngoing(true)
            .setSmallIcon(R.mipmap.ic_launcher)
            .setPriority(PRIORITY_MIN)
            .setCategory(Notification.CATEGORY_SERVICE)
            .build()

        locationForegroundClient
            .getLocationUpdates(LocationObjects.THIRTY_SECOND)
            .catch { e ->
                e.printStackTrace()
            }
            .onEach { location ->
                val mapChange: Map<String, Any> = mapOf(
                    "currentLocation" to GeoPoint(
                        location.latitude,
                        location.longitude
                    ),
                )
                changeLocationUseCase.invoke(
                    ChangeLocationUseCase.Param(
                        mapChange
                    )
                ).collect { changeLocationResult ->
                    if (changeLocationResult.isSuccess)
                        Log.i(TAG, LocationObjects.UPDATE_LOCATION)
                    else
                        stop()
                }
            }
            .launchIn(serviceScope)

        startForeground(2, notification)
    }

    @RequiresApi(Build.VERSION_CODES.O)
    private fun createNotificationChannel(): String {
        val chan = NotificationChannel(
            LocationObjects.CHANNEL_ID,
            LocationObjects.CHANNEL_NAME,
            NotificationManager.IMPORTANCE_NONE
        )
        chan.lightColor = Color.BLUE
        chan.lockscreenVisibility = Notification.VISIBILITY_PRIVATE
        val service = getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
        service.createNotificationChannel(chan)
        return LocationObjects.CHANNEL_ID
    }

    private fun stop() {
        Log.i(TAG, LocationObjects.SERVICE_FOREGROUND_STOP)
        stopForeground(true)
        stopSelf()
    }

    override fun onDestroy() {
        super.onDestroy()
        serviceScope.cancel()
    }

    companion object {
        const val TAG = "LocationForegroundService"
        const val ACTION_START = "ACTION_START"
        const val ACTION_STOP = "ACTION_STOP"
    }
}