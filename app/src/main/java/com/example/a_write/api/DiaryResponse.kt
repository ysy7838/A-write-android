package com.example.a_write.api

import java.io.Serializable

data class DiaryResult(
    val diaryId: Int,
    val title: String,
    val content: String,
    val imgUrl: String?,
    val secret: Boolean,
    val authorName: String,
    val authorProfile: Int,
    var heartby: Boolean
): Serializable