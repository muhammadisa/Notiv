package com.xoxoer.notivlibrary

import android.app.NotificationChannel
import android.app.NotificationManager
import android.app.PendingIntent
import android.content.Context
import android.content.Intent
import android.os.Build
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.NotificationCompat
import androidx.core.app.NotificationManagerCompat
import com.google.firebase.messaging.FirebaseMessagingService
import com.google.firebase.messaging.RemoteMessage
import kotlin.random.Random

abstract class NotivFirebaseService constructor(
    private val defaultActivity: AppCompatActivity,
    private val iconId: Int,
    private val channelId: String
) :
    FirebaseMessagingService() {

    override fun onNewToken(token: String) {
        Log.e("TOKEN_FROM_SERVICE", token)
    }

    override fun onMessageReceived(remoteMessage: RemoteMessage) {
        showNotification(remoteMessage)
    }

    private fun showNotification(remoteMessage: RemoteMessage) {
        val intent = Intent(this, defaultActivity::class.java).apply {
            flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
        }
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP)
        val pendingIntent = PendingIntent.getActivity(
            this, 0,
            intent, PendingIntent.FLAG_ONE_SHOT
        )

        val randomNotificationId = Random.nextInt()
        val notificationManager: NotificationManager =
            getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager

        val builder = NotificationCompat.Builder(this, channelId)
            .setSmallIcon(iconId)
            .setContentTitle(remoteMessage.notification?.title)
            .setContentText(remoteMessage.notification?.body)
            .setPriority(NotificationCompat.PRIORITY_DEFAULT)
            .setContentIntent(pendingIntent)

        when {
            Build.VERSION.SDK_INT >= Build.VERSION_CODES.O -> {
                val name = "Default"
                val descriptionText = "Default channel"
                val importance = NotificationManager.IMPORTANCE_DEFAULT
                val mChannel = NotificationChannel(channelId, name, importance)
                mChannel.description = descriptionText
                notificationManager.createNotificationChannel(mChannel)
            }
        }

        with(NotificationManagerCompat.from(this)) {
            notify(randomNotificationId, builder.build())
        }
    }
}