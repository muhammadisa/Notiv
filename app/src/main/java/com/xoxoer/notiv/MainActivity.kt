package com.xoxoer.notiv

import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import com.xoxoer.notivlibrary.NotivUtil.resolveClickAction
import com.xoxoer.notivlibrary.NotivUtil.resolveKeys
import com.xoxoer.notivlibrary.NotivUtil.resolveParcelable


class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        resolveClickAction(intent) {
            Log.e("GOTO", it.toString())
        }

        resolveParcelable<Person>(intent) { person ->
            Log.e("PARCELABLE NAME", person.name)
            Log.e("PARCELABLE AGE", person.age.toString())
        }

        resolveKeys(intent){
            Log.e("KEY_1", it.key1)
            Log.e("KEY_2", it.key2)
            Log.e("KEY_3", it.key3)
            Log.e("KEY_4", it.key4)
        }

    }
}