package com.xoxoer.notiv

import android.annotation.SuppressLint
import com.xoxoer.notivlibrary.NotivFirebaseService

@SuppressLint("MissingFirebaseInstanceTokenRefresh")
class AppFirebaseMessaging : NotivFirebaseService(
    channelId = "default",
    iconId = R.drawable.ic_launcher_foreground,
    defaultActivities = SplashActivity(),
    activities = mapOf(
        "MAIN" to MainActivity(),
        "SECOND" to SecondActivity()
    )
)