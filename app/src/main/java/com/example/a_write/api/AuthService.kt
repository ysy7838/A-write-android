package com.example.a_write.api

import android.util.Log
import com.example.a_write.LoginView
import com.example.a_write.SignUpView
import com.example.a_write.User
import com.example.a_write.UserLogin
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class AuthService {
    private lateinit var signUpView: SignUpView
    private lateinit var loginView: LoginView

    fun setSignUpView(signUpView: SignUpView) {
        this.signUpView = signUpView
    }

    fun setLoginView(loginView: LoginView) {
        this.loginView = loginView
    }

    fun signUp(user: User) {
        Log.d("Sign/REQUEST",user.toString())


        val authService = getRetrofit().create(AuthRetrofitInterface::class.java)
        authService.signUp(user).enqueue(object: retrofit2.Callback<AuthResponse>{

            override fun onResponse(call: Call<AuthResponse>, response: Response<AuthResponse>) {
                Log.d("SIGNUP/SUCCESS", response.toString())

                response.body()?.let { resp ->
                    when(resp.code){
                        "COMMON200" -> signUpView.onSignUpSuccess()
                        else -> signUpView.onSignUpFailure(resp.message)
                    }
                } ?: run {
                    // response.body()가 null인 경우의 처리
                    Log.d("SIGNUP/FAILURE", "응답 본문이 비어 있습니다.")

                    //서버랑 연결이 안될 때(network오류)
                    signUpView.onSignUpFailure("서버로부터 응답을 받지 못했습니다.")
                }

            }

            override fun onFailure(call: Call<AuthResponse>, t: Throwable) {
                Log.d("SIGNUP/FAILURE", t.message.toString())

            }


        })
        Log.d("SIGNUP", "HELLO")
    }


    fun login(user: UserLogin) {

        Log.d("Login/REQUEST",user.toString())

        val loginService = getRetrofit().create(AuthRetrofitInterface::class.java)

        loginService.login(user).enqueue(object : Callback<AuthResponse> {
            override fun onResponse(call: Call<AuthResponse>, response: Response<AuthResponse>) {
                Log.d("API LOGIN/SUCCESS", response.body().toString())
                Log.d("API LOGIN/SUCCESS", response.headers().toString())
                val sessionId = response.headers()["Set-Cookie"]
                Log.d("API sessionId", sessionId.toString())

                loginView.onSessionIdReceived(sessionId)

                response.body()?.let { resp ->
                    when(resp.code){
                        "COMMON200" -> loginView.onLoginSuccess()
                        else -> loginView.onLoginFailure(resp.message)
                    }
                } ?: run {
                    // response.body()가 null인 경우의 처리
                    Log.d("LOGIN/FAILURE", "응답 본문이 비어 있습니다.")

                    //서버랑 연결이 안될 때(network오류)
                    loginView.onLoginFailure("서버로부터 응답을 받지 못했습니다.")
                }
            }

            override fun onFailure(call: Call<AuthResponse>, t: Throwable) {
                Log.d("LOGIN/FAILURE", t.message.toString())

            }
        })
        Log.d("LOGIN", "HELLO")
    }
}