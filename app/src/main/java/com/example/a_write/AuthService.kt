package com.example.a_write

import android.util.Log
import android.view.View
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

        val authService = getRetrofit().create(AuthRetrofitInterface::class.java)
        authService.signUp(user).enqueue(object: retrofit2.Callback<AuthResponse>{

            override fun onResponse(call: Call<AuthResponse>, response: Response<AuthResponse>) {
                Log.d("SIGNUP/SUCCESS", response.toString())
                val resp: AuthResponse = response.body()!!
                when(resp.code){
                    "COMMON200"->signUpView.onSignUpSuccess()
                    else->signUpView.onSignUpFailure(resp.message)
                }

            }

            override fun onFailure(call: Call<AuthResponse>, t: Throwable) {
                Log.d("SIGNUP/FAILURE", t.message.toString())

            }


        })
        Log.d("SIGNUP", "HELLO")
    }


    fun login(user: UserLogin) {
        val loginService = getRetrofit().create(AuthRetrofitInterface::class.java)


        loginService.login(user).enqueue(object : Callback<AuthResponse> {
            override fun onResponse(call: Call<AuthResponse>, response: Response<AuthResponse>) {

                Log.d("LOGIN/SUCCESS", response.toString())

                val resp: AuthResponse = response.body()!!

                when (resp.code) {
                    "COMMON200" -> loginView.onLoginSuccess()//추후 변경
                    else -> loginView.onLoginFailure(resp.message)
                }


            }

            override fun onFailure(call: Call<AuthResponse>, t: Throwable) {
                Log.d("LOGIN/FAILURE", t.message.toString())

            }
        })
        Log.d("LOGIN", "HELLO")
    }
}