package com.xoxoer.notivlibrary

import android.content.Context
import android.content.Intent
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import com.google.firebase.iid.FirebaseInstanceId
import com.google.firebase.messaging.RemoteMessage
import com.google.gson.Gson

object NotivUtil {

    inline fun getFCM(crossinline onTokenOccurred: (token: String) -> Unit) {
        FirebaseInstanceId.getInstance().instanceId.addOnSuccessListener { instanceIdResult ->
            onTokenOccurred(instanceIdResult.token)
        }
    }

    inline fun resolveClickAction(
        intent: Intent,
        crossinline onResolved: (resolved: String) -> Unit
    ) {
        try {
            onResolved(intent.extras!!["click_action"].toString())
        } catch (e: NullPointerException) {
            Log.w("AUTO_REDIRECT", "Skip redirect, no action")
        }
    }

    inline fun <reified T> resolveRedirectResult(
        intent: Intent,
        crossinline onResolved: (resolved: Pair<Keys?, T?>) -> Unit
    ) {
        onResolved(
            Pair(
                intent.getParcelableExtra("redirect_keys"),
                Gson().fromJson(intent.getStringExtra("redirect_json_content"), T::class.java)
            )
        )
    }

    private fun resolveKeys(intent: Intent): Keys {
        return Keys(
            intent.extras!!["key_1"].toString(),
            intent.extras!!["key_2"].toString(),
            intent.extras!!["key_3"].toString(),
            intent.extras!!["key_4"].toString()
        )
    }

    fun resolveKeys(remoteMessage: RemoteMessage): Keys {
        return Keys(
            remoteMessage.data["key_1"].toString(),
            remoteMessage.data["key_2"].toString(),
            remoteMessage.data["key_3"].toString(),
            remoteMessage.data["key_4"].toString()
        )
    }

    fun redirectWithIntentData(
        ctx: Context,
        intent: Intent,
        activity: Class<out AppCompatActivity>
    ): Intent {
        return Intent(ctx, activity)
            .putExtra("redirect_keys", resolveKeys(intent))
            .putExtra("redirect_json_content", intent.extras!!["json"].toString())
    }

    fun activityMapper(
        action: String?,
        activities: Map<String, AppCompatActivity>
    ): Class<out AppCompatActivity> {
        return (activities[action] ?: error(""))::class.java
    }
}