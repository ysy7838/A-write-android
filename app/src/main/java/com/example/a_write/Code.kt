package com.example.a_write

import com.google.gson.annotations.SerializedName

data class CodeSend(
    @SerializedName(value = "email") var email: String
)

data class CodeCheck(
    @SerializedName(value = "email") var email: String,
    @SerializedName(value = "code") var code: String
)

data class EmailSend(
    @SerializedName(value = "email") var email: String
)

data class Password(
    @SerializedName(value = "newPassword") var newPassword: String,
    @SerializedName(value = "rewritePassword") var rewritePassword: String
)

