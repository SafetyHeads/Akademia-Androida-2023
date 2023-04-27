package com.safetyheads.data.network.retrofit

import com.jakewharton.retrofit2.adapter.kotlin.coroutines.CoroutineCallAdapterFactory
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class ApiClient(private val isDebug: Boolean) {

    private val retrofitBuilders = mutableMapOf<String, Retrofit.Builder>()

    fun <T> create(baseUrl: String, serviceClass: Class<T>): T {
        val retrofit = retrofitBuilders.getOrPut(baseUrl) {
            Retrofit.Builder()
                .baseUrl(baseUrl)
                .client(createOkHttpClient())
                .addConverterFactory(GsonConverterFactory.create())
                .addCallAdapterFactory(CoroutineCallAdapterFactory())
        }.build()

        return retrofit.create(serviceClass)
    }

    private fun createOkHttpClient(): OkHttpClient {
        return OkHttpClient.Builder()
            .addDebugInterceptor()
            .build()
    }

    private fun OkHttpClient.Builder.addDebugInterceptor(): OkHttpClient.Builder {
        if (isDebug) {
            addInterceptor(HttpLoggingInterceptor().setLevel(HttpLoggingInterceptor.Level.BODY))
        }
        return this
    }

}
