package com.example.a_write

interface LoginView {
    fun onLoginSuccess(code: Int )//result 추가
    fun onLoginFailure()
}