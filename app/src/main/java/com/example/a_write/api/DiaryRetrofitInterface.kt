package com.example.a_write.api

import okhttp3.MultipartBody
import okhttp3.RequestBody
import retrofit2.Call
import retrofit2.http.DELETE
import retrofit2.http.GET
import retrofit2.http.Multipart
import retrofit2.http.POST
import retrofit2.http.Part
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

    @Multipart
    @POST("/diary/write")
    fun postDiary(
        @Part("title") title: RequestBody,
        @Part("content") content: RequestBody,
        @Part image: MultipartBody.Part,
        @Part("secret") secret: RequestBody,
        @Part("theme") theme: RequestBody,
        @Part("date") date: RequestBody
    ): Call<Void>

}