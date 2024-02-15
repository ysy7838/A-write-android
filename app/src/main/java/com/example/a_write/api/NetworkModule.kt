package com.example.a_write.api

import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

const val BASE_URL = "https://5dddfdcc-3d90-4f61-970e-af2ff5c401ef.mock.pstmn.io"

fun getRetrofit(): Retrofit {

    return Retrofit.Builder().baseUrl(BASE_URL)
        .addConverterFactory(GsonConverterFactory.create())
        .build()
}