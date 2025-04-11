package com.app.animego.utils

import java.text.ParseException
import java.text.SimpleDateFormat
import java.util.Locale
import android.content.Context
import android.net.ConnectivityManager
import android.net.NetworkCapabilities
import android.os.Build

class AppUtils {
    companion object {

        fun isInternetAvailable(context: Context): Boolean {
            var result = false
            val connectivityManager =
                context.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                val networkCapabilities = connectivityManager.activeNetwork ?: return false
                val actNw =
                    connectivityManager.getNetworkCapabilities(networkCapabilities) ?: return false
                result = when {
                    actNw.hasTransport(NetworkCapabilities.TRANSPORT_WIFI) -> true
                    actNw.hasTransport(NetworkCapabilities.TRANSPORT_CELLULAR) -> true
                    actNw.hasTransport(NetworkCapabilities.TRANSPORT_ETHERNET) -> true
                    else -> false
                }
            } else {
                connectivityManager.run {
                    connectivityManager.activeNetworkInfo?.run {
                        result = when (type) {
                            ConnectivityManager.TYPE_WIFI -> true
                            ConnectivityManager.TYPE_MOBILE -> true
                            ConnectivityManager.TYPE_ETHERNET -> true
                            else -> false
                        }

                    }
                }
            }

            return result
        }


        fun getDateFormatted(dateString: String): String? {
            try {
                if (dateString.isNotEmpty()) {
                    val currentFormat = SimpleDateFormat("yyyy-MM-dd", Locale.ENGLISH)
                    val requiredFormat = SimpleDateFormat("dd MMM yyyy", Locale.ENGLISH)
                    val date = currentFormat.parse(dateString)
                    val dateFormatted = requiredFormat.format(date!!)
                    return dateFormatted
                }
                return null
            } catch (e: ParseException) {
                e.printStackTrace()
            } catch (e: Exception) {
                e.printStackTrace()
            }
            return null
        }

        fun getDurationFormatted(runtime: Int): String? {
            try {
                val hour = runtime / 60
                val minutes = runtime % 60
                return "${hour}h ${minutes}m"
            } catch (e: Exception) {
                e.printStackTrace()
            }
            return null
        }
    }
}