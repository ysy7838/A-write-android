package com.example.a_write

import com.google.gson.annotations.SerializedName

data class AuthResponse(

    @SerializedName("isSuccess") val isSuccess: Boolean,
    @SerializedName("code") val code: String,
    @SerializedName("message") val message: String,

)