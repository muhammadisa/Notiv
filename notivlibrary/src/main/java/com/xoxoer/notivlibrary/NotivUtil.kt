package com.xoxoer.notivlibrary

import android.content.Intent
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.google.firebase.iid.FirebaseInstanceId
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

    inline fun <reified T> resolveParcelable(
        intent: Intent,
        crossinline onResolved: (resolved: T) -> Unit
    ) {
        try {
            val parcelable = intent.extras!!["parcelable"].toString()
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
}