package com.example.a_write.api

import com.google.gson.annotations.SerializedName
import java.io.Serializable

data class MyPageDiary(
    val diaryId: Int,
    val title: String,
    val content: String,
    val imgUrl: String?,
    val secret: Boolean,
    val authorName: String,
    val authorProfile: Int,
    var heartby: Boolean,
    val theme: Int,
    val date: String,
    val heartsCount: Int
) : Serializable

data class UserInfo(
    val profileImg: Int,
    val nickname: String,
    val email: String
)
