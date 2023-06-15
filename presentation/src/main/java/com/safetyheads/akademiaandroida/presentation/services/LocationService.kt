package com.safetyheads.akademiaandroida.presentation.services

import android.app.Notification
import android.app.Notification.PRIORITY_MIN
import android.app.NotificationChannel
import android.app.NotificationManager
import android.app.Service
import android.content.Context
import android.content.Intent
import android.graphics.Color
import android.location.Location
import android.os.Build
import android.os.IBinder
import android.util.Log
import androidx.annotation.RequiresApi
import androidx.core.app.NotificationCompat
import com.google.android.gms.location.LocationServices
import com.safetyheads.akademiaandroida.domain.usecases.ChangeLocationUseCase
import com.safetyheads.akademiaandroida.presentation.R
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.SupervisorJob
import kotlinx.coroutines.cancel
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.withContext
import org.koin.android.ext.android.inject

class LocationService : Service() {

    private val serviceScope = CoroutineScope(SupervisorJob() + Dispatchers.IO)
    private lateinit var locationForegroundClient: LocationClient
    private lateinit var locationBackgroundClient: LocationClient
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
        locationBackgroundClient = DefaultLocationClient(
            applicationContext,
            LocationServices.getFusedLocationProviderClient(applicationContext),
            LocationType.Background
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
        val channelId =
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
                createNotificationChannel("my_service", "My Background Service")
            } else {
                ""
            }

        val notificationBuilder = NotificationCompat.Builder(this, channelId)
        val notification = notificationBuilder.setOngoing(true)
            .setSmallIcon(R.mipmap.ic_launcher)
            .setPriority(PRIORITY_MIN)
            .setCategory(Notification.CATEGORY_SERVICE)
            .build()

        locationForegroundClient
            .getLocationUpdates(5 * 1000L)
            .catch { e -> e.printStackTrace() }
            .onEach { location ->
                changeLocalization(location)
            }
            .launchIn(serviceScope)

        locationBackgroundClient
            .getLocationUpdates(5 * 60 * 1000L)
            .catch { e -> e.printStackTrace() }
            .onEach { location ->
                changeLocalization(location)
            }
            .launchIn(serviceScope)

        startForeground(1, notification)
    }

    @RequiresApi(Build.VERSION_CODES.O)
    private fun createNotificationChannel(channelId: String, channelName: String): String {
        val chan = NotificationChannel(
            channelId,
            channelName, NotificationManager.IMPORTANCE_NONE
        )
        chan.lightColor = Color.BLUE
        chan.lockscreenVisibility = Notification.VISIBILITY_PRIVATE
        val service = getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
        service.createNotificationChannel(chan)
        return channelId
    }

    private suspend fun changeLocalization(localization: Location) {
        withContext(Dispatchers.IO) {
            val mapChange: Map<String, Any> = mapOf(
                "currentLocation" to localization
            )

            changeLocationUseCase.invoke(
                ChangeLocationUseCase.Param(
                    mapChange,
                    "changeCurrentLocation"
                )
            ).collect { locationResult ->
                if (locationResult.isSuccess) {
                    Log.i("LocationService", locationResult.getOrNull().toString())
                } else {
                    Log.i(
                        "LocationService",
                        locationResult.exceptionOrNull()?.message.orEmpty()
                    )
                }
            }
        }
    }

    private fun stop() {
        stopForeground(true)
        stopSelf()
    }

    override fun onDestroy() {
        super.onDestroy()
        serviceScope.cancel()
    }

    companion object {
        const val ACTION_START = "ACTION_START"
        const val ACTION_STOP = "ACTION_STOP"
    }
}