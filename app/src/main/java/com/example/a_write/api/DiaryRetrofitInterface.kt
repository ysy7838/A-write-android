package com.example.a_write.api

import retrofit2.Call
import retrofit2.http.DELETE
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.Path

interface DiaryRetrofitInterface {
    @GET("/home")
    fun getHomeDiaries(): Call<List<DiaryResult>>

    @GET("/hearts")
    fun getHeartDiaries(): Call<List<DiaryResult>>

    @POST("/home/hearts/{diaryId}")
    fun postHeart(@Path("diaryId") diaryId: Int): Call<Void>

    @DELETE("/home/hearts/{diaryId}")
    fun deleteHeart(@Path("diaryId") diaryId: Int): Call<Void>

}