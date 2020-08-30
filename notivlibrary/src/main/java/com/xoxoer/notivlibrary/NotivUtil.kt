package com.xoxoer.notivlibrary

import android.content.Intent
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
            e.printStackTrace()
        }
    }

    inline fun <reified T> resolveJson(
        intent: Intent,
        crossinline onResolved: (resolved: T) -> Unit
    ) {
        try {
            val parcelable = intent.extras!!["json"].toString()
            onResolved(Gson().fromJson(parcelable, T::class.java))
        } catch (e: NullPointerException) {
            e.printStackTrace()
        }
    }

    inline fun resolveKeys(
        intent: Intent,
        crossinline onResolved: (resolved: Keys) -> Unit
    ) {
        try {
            onResolved(
                Keys(
                    intent.extras!!["key_1"].toString(),
                    intent.extras!!["key_2"].toString(),
                    intent.extras!!["key_3"].toString(),
                    intent.extras!!["key_4"].toString()
                )
            )
        } catch (e: NullPointerException) {
            e.printStackTrace()
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

    fun resolveKeys(remoteMessage: RemoteMessage): Keys {
        return Keys(
            remoteMessage.data["key_1"].toString(),
            remoteMessage.data["key_2"].toString(),
            remoteMessage.data["key_3"].toString(),
            remoteMessage.data["key_4"].toString()
        )
    }

    fun activityMapper(
        action: String?,
        activities: Map<String, AppCompatActivity>
    ): Class<out AppCompatActivity> {
        return (activities[action] ?: error(""))::class.java
    }
}