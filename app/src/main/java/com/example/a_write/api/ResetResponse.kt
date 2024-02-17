package com.example.a_write.api

import com.google.gson.annotations.SerializedName

data class ResetResponse (
    @SerializedName("isSuccess") val isSuccess: Boolean,
    @SerializedName("code") val code: String,
    @SerializedName("message") val message: String,
    @SerializedName("result") val result:String
)
