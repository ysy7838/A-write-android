package com.example.a_write

interface CodeView {
    fun onEmailCodeSuccess()
    fun onEmailCodeFailure(message: String)
}

interface CodeCheckView{
    fun onSuccess()
    fun onFailure(message: String)
}

interface ResetEmailView{
    fun onSuccess(email:String)
    fun onFailure(message: String)
}

interface ResetPasswordView{
    fun onSuccess()
    fun onFailure(message: String)
}