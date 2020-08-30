package com.xoxoer.notiv

import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import com.xoxoer.notivlibrary.NotivUtil.resolveRedirectResult


class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        resolveRedirectResult<Person>(intent) {
            Log.e("KEY_1", it.first?.key1.toString())
            Log.e("KEY_2", it.first?.key2.toString())
            Log.e("KEY_3", it.first?.key3.toString())
            Log.e("KEY_4", it.first?.key4.toString())

            Log.e("PARCELABLE NAME", it.second?.name.toString())
            Log.e("PARCELABLE AGE", it.second?.age.toString())
        }

    }
}