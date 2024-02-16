package com.example.a_write

import com.google.gson.annotations.SerializedName


data class User(
    @SerializedName(value = "email") var email: String,
    @SerializedName(value = "password") var password: String,
    @SerializedName(value = "nickname") var nickname: String
)

data class UserLogin(
    @SerializedName(value = "email") var id: String,
    @SerializedName(value = "password") var password: String,
)