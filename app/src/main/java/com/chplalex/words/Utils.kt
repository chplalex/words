package com.chplalex.words

import android.content.Context
import android.net.ConnectivityManager
import android.net.NetworkInfo
import android.view.View
import com.google.android.material.textfield.TextInputEditText

fun TextInputEditText.isNullOrEmpty() = text.isNullOrEmpty()
fun TextInputEditText.isNotNullNotEmpty() = text != null && text.toString().isNotEmpty()
fun TextInputEditText.makeEmpty() { setText("") }

fun View.makeEnabled() { isEnabled = true }
fun View.makeDisabled() { isEnabled = false }
fun View.makeVisible() { visibility = View.VISIBLE}
fun View.makeInVisible() { visibility = View.INVISIBLE}
fun View.makeGone() { visibility = View.GONE }

fun isOnline(context: Context): Boolean {
    // TODO: fix deprecated methods
    val connectivityManager = context.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
    val netInfo: NetworkInfo?
    netInfo = connectivityManager.activeNetworkInfo
    return netInfo != null && netInfo.isConnected
}
