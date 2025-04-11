package com.app.animego.utils

import android.content.Context
import okhttp3.Interceptor
import okhttp3.Response
import java.io.IOException

class NetworkAvailabilityInterceptor(private val context: Context) : Interceptor {
    override fun intercept(chain: Interceptor.Chain): Response {
        if (!AppUtils.isInternetAvailable(context)) {
            throw NoInternetException("No internet connection")
        }
        return chain.proceed(chain.request())
    }
}

class NoInternetException(message: String) : IOException(message)