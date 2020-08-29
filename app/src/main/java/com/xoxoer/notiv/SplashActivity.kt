package com.xoxoer.notiv

import android.content.Intent
import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
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
        resolveClickAction(intent) {
            when (it) {
                "MAIN" -> startActivity(Intent(this, MainActivity::class.java).addFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP))
                "SECOND" -> startActivity(Intent(this, SecondActivity::class.java).addFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP))
            }
        }
    }
}