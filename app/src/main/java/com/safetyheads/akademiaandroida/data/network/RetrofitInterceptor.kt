package com.safetyheads.akademiaandroida.data.network

import android.util.Log
import okhttp3.Interceptor
import okhttp3.Response

class RetrofitInterceptor : Interceptor {
    override fun intercept(chain: Interceptor.Chain): Response {
        val request = chain.request()
        Log.d("Retrofit", "Sending request: $request")
        val response = chain.proceed(request)
        Log.d("Retrofit", "Received response: ${response.body()?.string()}")
        return response
    }
}