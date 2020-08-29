package com.xoxoer.notivlibrary

import android.app.NotificationChannel
import android.app.NotificationManager
import android.app.PendingIntent
import android.app.TaskStackBuilder
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
    private val defaultActivities: AppCompatActivity,
    private val activities: Map<String, AppCompatActivity>,
    private val iconId: Int,
    private val channelId: String
) : FirebaseMessagingService() {

    override fun onNewToken(token: String) {
        Log.e("TOKEN_FROM_SERVICE", token)
    }

    override fun onMessageReceived(remoteMessage: RemoteMessage) {
        showNotification(remoteMessage)
    }

    private fun showNotification(remoteMessage: RemoteMessage) {
        val randomNotificationId = Random.nextInt()
        val clickAction = remoteMessage.data["click_action"]
        val activity = if (!clickAction.isNullOrEmpty()) {
            (activities[clickAction] ?: error(""))::class.java
        } else {
            defaultActivities::class.java
        }
        val resultIntent = Intent(this, activity)
        resultIntent.addFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP)
        val resultPendingIntent: PendingIntent = TaskStackBuilder.create(this).run {
            addNextIntentWithParentStack(resultIntent)
            getPendingIntent(randomNotificationId, PendingIntent.FLAG_UPDATE_CURRENT)
        }

        val notificationManager: NotificationManager =
            getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager

        val builder = NotificationCompat.Builder(this, channelId)
            .setSmallIcon(iconId)
            .setContentTitle(remoteMessage.notification?.title)
            .setContentText(remoteMessage.notification?.body)
            .setPriority(NotificationCompat.PRIORITY_DEFAULT)
            .setContentIntent(resultPendingIntent)
            .setAutoCancel(true)

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