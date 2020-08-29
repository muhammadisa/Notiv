package com.xoxoer.notiv

import android.content.Intent
import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import com.xoxoer.notivlibrary.NotivUtil

class SplashActivity : AppCompatActivity() {



    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_splash)
        autoRedirect()
    }

    private fun autoRedirect() {
        NotivUtil.resolveGoto(intent) {
            Log.e("GOTO", it.toString())
            it.observeForever { goto ->
                when (goto) {
                    "MAIN" -> startActivity(Intent(this, MainActivity::class.java))
                    "SECOND" -> startActivity(Intent(this, SecondActivity::class.java))
                }
            }
        }
    }
}