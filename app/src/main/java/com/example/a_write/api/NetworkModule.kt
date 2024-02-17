package com.example.a_write.api

import android.content.Context
import android.util.Log
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

const val BASE_URL = "http://43.201.161.83:8080"

fun getRetrofit(): Retrofit {
    return Retrofit.Builder().baseUrl(BASE_URL)
        .addConverterFactory(GsonConverterFactory.create())
        .build()
}

fun getUserRetrofit(context : Context): Retrofit {
    val spf = context.getSharedPreferences("auth2", Context.MODE_PRIVATE)
    val sessionId = spf.getString("Session", "") ?: ""
    val client = OkHttpClient.Builder()
        .addInterceptor { chain ->
            val original = chain.request()
            val request = original.newBuilder()
                .header("Cookie", sessionId)
                .method(original.method, original.body)
                .build()
            chain.proceed(request)
        }
        .build()

    return Retrofit.Builder().baseUrl(BASE_URL)
        .client(client)
        .addConverterFactory(GsonConverterFactory.create())
        .build()
}