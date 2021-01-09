package com.emiliorg.myrestaurant.utils

import android.app.Activity
import android.content.Context
import android.net.ConnectivityManager
import java.util.*

object netutils {
    fun hayConexion(a: Activity?): Boolean {
        val connectivityManager = Objects.requireNonNull(a)?.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
        val activeNetworkInfo = Objects.requireNonNull(connectivityManager).activeNetworkInfo
        return activeNetworkInfo != null && activeNetworkInfo.isConnected
    }
}