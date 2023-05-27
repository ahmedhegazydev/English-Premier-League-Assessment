package com.carefer.englishpremierleague.util

import android.content.Context
import android.net.ConnectivityManager
import android.net.NetworkCapabilities
import android.os.Build
import dagger.hilt.android.qualifiers.ApplicationContext
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
/* The NetworkHelper class checks if the device is connected to a network. */
class NetworkHelper @Inject constructor(@ApplicationContext private val context: Context) {

    @Suppress("DEPRECATION")
            /**
             * The function checks if the device is connected to a network and returns a boolean value.
             *
             * @return a Boolean value which indicates whether the device is connected to a network or not.
             */
    fun isNetworkConnected(): Boolean {
        val result: Boolean
        val connectivityManager =
/* The NetworkHelper class checks if the device is connected to a network. */
            context.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            val networkCapabilities = connectivityManager.activeNetwork ?: return false
            val activeNetwork =
                connectivityManager.getNetworkCapabilities(networkCapabilities) ?: return false
            result = when {
                activeNetwork.hasTransport(NetworkCapabilities.TRANSPORT_WIFI) -> true
                activeNetwork.hasTransport(NetworkCapabilities.TRANSPORT_CELLULAR) -> true
                activeNetwork.hasTransport(NetworkCapabilities.TRANSPORT_ETHERNET) -> true
                else -> false
            }
        } else {
            val networkInfo =
                connectivityManager.activeNetworkInfo ?: return false
            return networkInfo.isConnected
        }

        return result
    }
}