package com.safetyheads.data.network.retrofit

import com.safetyheads.data.network.`object`.CustomLog
import okhttp3.Interceptor
import okhttp3.Response

class RetrofitInterceptor(
    private val customLog: CustomLog
): Interceptor {
    override fun intercept(chain: Interceptor.Chain): Response {
        val request = chain.request()
        customLog.d("Retrofit", "Sending request: $request")
        val response = chain.proceed(request)
        customLog.d("Retrofit", "Received response: ${response.body()}")
        return response
    }
}