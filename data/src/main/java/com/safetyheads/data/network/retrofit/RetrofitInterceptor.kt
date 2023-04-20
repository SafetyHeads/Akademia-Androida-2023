package com.safetyheads.data.network.retrofit

import okhttp3.Interceptor
import okhttp3.Response

class RetrofitInterceptor(private val appLogger: AppLogger) : Interceptor {
    override fun intercept(chain: Interceptor.Chain): Response {
        val request = chain.request()
        appLogger.d("Retrofit", "Sending request: $request")
        val response = chain.proceed(request)
        appLogger.d("Retrofit", "Received response: ${response.body()}")
        return response
    }
}