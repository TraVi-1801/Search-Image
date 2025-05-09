package com.vic_project.search_image.data.remote.service

import com.vic_project.search_image.data.remote.network.InternetAvailabilityRepository
import okhttp3.Interceptor
import okhttp3.Response
import java.io.IOException


class NoInternetInterceptor(
    private val network: InternetAvailabilityRepository
) : Interceptor {
    override fun intercept(chain: Interceptor.Chain): Response {
        if (network.isConnected.value.not()) {
            throw IOException("NO_INTERNET")
        }
        return chain.proceed(chain.request())
    }
}