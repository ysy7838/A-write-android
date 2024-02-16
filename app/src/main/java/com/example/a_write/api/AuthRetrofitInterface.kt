package com.example.a_write.api

import com.example.a_write.User
import com.example.a_write.UserLogin
import retrofit2.Call
import retrofit2.http.Body
import retrofit2.http.POST

interface AuthRetrofitInterface {
    @POST("/users/join")
    fun signUp(@Body user: User): Call<AuthResponse>

    @POST("/users/login")
    fun login(@Body user: UserLogin): Call<AuthResponse>

}