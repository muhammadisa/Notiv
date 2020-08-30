package com.xoxoer.notivlibrary

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

@Parcelize
data class Keys(
    val key1: String,
    val key2: String,
    val key3: String,
    val key4: String
): Parcelable