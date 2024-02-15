package com.example.a_write.api

import com.google.gson.JsonObject
import retrofit2.Call
import retrofit2.http.Body
import retrofit2.http.DELETE
import retrofit2.http.GET
import retrofit2.http.PATCH
import retrofit2.http.POST
import retrofit2.http.Path

interface MyPageRetrofitInterface {
    @GET("/myPage/users/heart")
    fun getMyPageDiaries(): Call<List<MyPageDiary>>

    @GET("/myPage/users/{date}")
    fun getCalenderDiary(@Path("date") date: String): Call<MyPageDiary>

    @GET("/myPage/users/info")
    fun getUserInformation(): Call<UserInfo>

    @PATCH("/myPage/users/profile-image")
    fun updateProfile(@Body profileData: JsonObject): Call<Void>

    @PATCH("/myPage/users/nickname")
    fun updateNickname(@Body profileData: JsonObject): Call<Void>

    @POST("/myPage/users/withdrawal")
    fun postWithdrawal(@Body passwordData: JsonObject): Call<Void>

    @DELETE("/myPage/users/withdrawal")
    fun deleteWithdrawal(): Call<Void>

    @DELETE("/diary/delete/{diaryId}")
    fun deleteDiary(@Path("diaryId") diaryId: Int): Call<Void>

}