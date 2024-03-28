package com.rais.chucknorris.data.remote.response

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class Jokes(
    val categories: ArrayList<String>,
    val created_at: String,
    val icon_url: String,
    val updated_at: String,
    val url: String,
    val value: String
) : Parcelable
