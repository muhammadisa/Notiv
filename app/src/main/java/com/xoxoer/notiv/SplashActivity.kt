package com.xoxoer.notiv

import android.content.Intent
import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import com.xoxoer.notivlibrary.NotivUtil.activityMapper
import com.xoxoer.notivlibrary.NotivUtil.getFCM
import com.xoxoer.notivlibrary.NotivUtil.resolveClickAction

class SplashActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_splash)

        autoRedirect()

        getFCM {
            Log.e("FCM_RETRIEVED", it)
        }
    }

    private fun autoRedirect() {
        resolveClickAction(intent) { action ->
            val activity = activityMapper(action, Constant.activityMapper)
            startActivity(Intent(this, activity))
        }
    }
}