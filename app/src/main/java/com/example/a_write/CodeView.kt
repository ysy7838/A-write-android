package com.example.a_write

interface CodeView {
    fun onEmailCodeSuccess()
    fun onEmailCodeFailure(message: String)
}

interface CodeCheckView{
    fun onSuccess()
    fun onFailure(message: String)
}