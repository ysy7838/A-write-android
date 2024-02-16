package com.example.a_write.api

import retrofit2.Call
import retrofit2.http.Body
import retrofit2.http.POST

interface CodeRetrofitInterface {
    @POST("/users/join/email-check")
    fun sendCode(@Body email:String): Call<CodeResponse>

    @POST("/users/join/code-verification")
    fun verificationCode(@Body email:String)
}