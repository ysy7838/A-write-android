package com.example.a_write.api

import com.example.a_write.EmailSend
import com.example.a_write.Password
import okhttp3.ResponseBody
import retrofit2.Call
import retrofit2.http.Body
import retrofit2.http.POST
import retrofit2.http.Url

interface ResetRetrofitInterface {
    @POST("/myPage/users/password-reset/mail")
    fun sendEmail(@Body email: EmailSend): Call<ResponseBody>

    @POST
    fun reset(@Url url: String, @Body password: Password): Call<ResetResponse>
}