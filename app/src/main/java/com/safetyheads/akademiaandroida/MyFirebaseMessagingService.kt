package com.safetyheads.akademiaandroida

import android.app.NotificationChannel
import android.app.NotificationManager
import android.app.PendingIntent
import android.content.Context
import android.content.Intent
import android.os.Build
import android.util.Log
import androidx.core.app.NotificationCompat
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.messaging.FirebaseMessagingService
import com.google.firebase.messaging.RemoteMessage
import com.safetyheads.akademiaandroida.presentation.ui.MainActivity


class MyFirebaseMessagingService : FirebaseMessagingService() {
    override fun onMessageReceived(remoteMessage: RemoteMessage) {
        super.onMessageReceived(remoteMessage)

        val title = remoteMessage.notification?.title
        val body = remoteMessage.notification?.body
        val type = remoteMessage.data["type"]

        if (type == "message") {
            title?.let { t ->
                body?.let { b ->
                    showNotification(t, b)
                }
            }
        }
        if (type == "silent") {
            title?.let { t ->
                body?.let { b ->
                    //      showNotificationSilent(t, b)
                }
            }
        }

    }

    override fun onNewToken(token: String) {
        super.onNewToken(token)
        Log.d("token", token)

        /*
        saveTokenToFireStore(token)
         val currentUser = FirebaseAuth.getInstance().currentUser
         if (currentUser != null) {
             saveTokenToFireStore(token, currentUser.uid)
         } else {
             Log.w("MyFirebaseMessaging", "User not logged in, can't save token")
      }
       */


    }

    private fun showNotification(title: String, message: String) {
        val builder = NotificationCompat.Builder(this, "channel_id")
            .setSmallIcon(com.safetyheads.akademiaandroida.presentation.R.id.logo_safetyheads) // Ikona aplikacji
            .setContentTitle(title)
            .setContentText(message)
            .setAutoCancel(true)

        val intent = Intent(this, MainActivity::class.java)
        val pendingIntent =
            PendingIntent.getActivity(this, 0, intent, PendingIntent.FLAG_UPDATE_CURRENT)
        builder.setContentIntent(pendingIntent)

        val notificationManager =
            getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager

        // Dla Android Oreo (API 26) i wyższych, dodaj kanał powiadomień
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            val channel = NotificationChannel(
                "channel_id",
                "Channel name",
                NotificationManager.IMPORTANCE_DEFAULT
            )
            notificationManager.createNotificationChannel(channel)
        }
        notificationManager.notify(0, builder.build())
    }

    private fun saveTokenToFireStore(token: String, userId: String) {
        val fireStore = FirebaseFirestore.getInstance()
        val tokenDocument = hashMapOf("token" to token)

        fireStore.collection("users").document(userId).collection("tokens").add(tokenDocument)
            .addOnSuccessListener { documentReference ->
                Log.d(
                    "MyFirebaseMessaging",
                    "Token successfully saved in Firestore: ${documentReference.id}"
                )
            }
            .addOnFailureListener { exception ->
                Log.w("MyFirebaseMessaging", "Error saving token in Firestore", exception)
            }

    }
}
