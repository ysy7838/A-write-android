package com.example.a_write

interface LoginView {
    fun onLoginSuccess()//result 추가
    fun onLoginFailure(message:String)
    fun onSessionIdReceived(sessionId: String?)
}