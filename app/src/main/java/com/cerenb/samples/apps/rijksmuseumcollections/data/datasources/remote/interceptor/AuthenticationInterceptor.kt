package com.cerenb.samples.apps.rijksmuseumcollections.data.datasources.remote.interceptor

import okhttp3.Interceptor
import okhttp3.Response

class AuthenticationInterceptor : Interceptor {

    override fun intercept(chain: Interceptor.Chain): Response {
        val originalRequest = chain.request()
        val url = originalRequest.url.newBuilder()
            .addQueryParameter("key", API_KEY)
            .build()

        val request = originalRequest.newBuilder().url(url).build()
        return chain.proceed(request)
    }

    companion object {
        const val API_KEY = "0fiuZFh4"
    }

}