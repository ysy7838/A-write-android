package com.example.a_write.api

import com.example.a_write.CodeCheck
import com.example.a_write.CodeSend
import retrofit2.Call
import retrofit2.http.Body
import retrofit2.http.POST

interface CodeRetrofitInterface {
    @POST("/users/join/email-check")
    fun sendCode(@Body code: CodeSend): Call<CodeResponse>

    @POST("/users/join/code-verification")
    fun verificationCode(@Body code:CodeCheck): Call<CodeResponse>
}