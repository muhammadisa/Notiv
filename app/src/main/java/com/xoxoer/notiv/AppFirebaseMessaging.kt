package com.xoxoer.notiv

import android.annotation.SuppressLint
import com.xoxoer.notivlibrary.NotivFirebaseService

@SuppressLint("MissingFirebaseInstanceTokenRefresh")
class AppFirebaseMessaging : NotivFirebaseService(
    defaultActivity = SplashActivity(),
    iconId = R.drawable.ic_launcher_foreground,
    channelId = "default"
)