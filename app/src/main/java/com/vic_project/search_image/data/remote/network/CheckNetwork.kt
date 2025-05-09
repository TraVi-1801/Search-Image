package com.vic_project.search_image.data.remote.network

import android.content.Context
import android.net.ConnectivityManager
import android.net.NetworkCapabilities
import android.os.Build.VERSION
import android.os.Build.VERSION_CODES
import androidx.core.content.getSystemService

object CheckNetwork {
    @Suppress("DEPRECATION")
    fun isHasNetwork(context: Context) : Boolean {
        val connectivityManager = context.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
        when {
            VERSION.SDK_INT >= VERSION_CODES.M -> {
                val activeNetwork = connectivityManager.activeNetwork ?: return false
                val networkCapabilities = connectivityManager.getNetworkCapabilities(activeNetwork) ?: return false
                return  networkCapabilities.hasCapability(NetworkCapabilities.NET_CAPABILITY_INTERNET)
            }
            else -> {
                val activeNetworkInfo = connectivityManager.activeNetworkInfo ?: return false
                return activeNetworkInfo.isConnected
            }
        }
    }
}