package com.example.a_write.api

import android.util.Log
import com.example.a_write.EmailSend
import com.example.a_write.Password
import com.example.a_write.ResetEmailView
import com.example.a_write.ResetPasswordView
import okhttp3.ResponseBody
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class ResetService {
    private lateinit var resetEmailView: ResetEmailView

    private lateinit var resetPasswordView: ResetPasswordView


    fun setEmailView(resetEmailView: ResetEmailView) {
        this.resetEmailView = resetEmailView
    }

    fun setPasswordView(resetPasswordView: ResetPasswordView){
        this.resetPasswordView = resetPasswordView
    }

    fun sendEmail(email: EmailSend) {
        Log.d("email/REQUEST", email.toString())

        val resetService = getRetrofit().create(ResetRetrofitInterface::class.java)

        resetService.sendEmail(email).enqueue(object: Callback<ResponseBody> {

            override fun onResponse(call: Call<ResponseBody>, response: Response<ResponseBody>) {
                if (response.isSuccessful) {
                    // HTTP 상태 코드가 200-299 범위인 경우
                    val statusCode = response.code()
                    // 응답 본문을 처리합니다. 예: response.body().string()
                    Log.d("email/SUCCESS", "Status Code: $statusCode")
                    resetEmailView.onSuccess(email.email)
                } else {
                    // HTTP 상태 코드가 200-299 범위 밖인 경우 (예: 4xx, 5xx 오류)
                    val statusCode = response.code()
                    Log.d("email/ERROR", "Error Status Code: $statusCode")
                    resetEmailView.onFailure("메일 발송을 실패하였습니다")
                }
            }

            override fun onFailure(call: Call<ResponseBody>, t: Throwable) {
                // 네트워크 요청 자체가 실패한 경우 (예: 인터넷 연결 문제)
                Log.d("email/FAILURE", "Failure: ${t.message}")
            }
        })
        Log.d("email", "HELLO")
    }



    fun passwordCheck(password: Password, token:String) {
        Log.d("Password/REQUEST", password.toString())

        val resetService = getRetrofit().create(ResetRetrofitInterface::class.java)

        val url = "http://43.201.161.83:8080/myPage/users/password-reset?token=$token"


        resetService.reset(url, password).enqueue(object: retrofit2.Callback<ResetResponse>{

            override fun onResponse(call: Call<ResetResponse>, response: Response<ResetResponse>) {
                Log.d("Password/SUCCESS", response.toString())

                response.body()?.let { resp ->
                    when(resp.code){
                        "COMMON200" -> resetPasswordView.onSuccess()
                        else -> resetPasswordView.onFailure(resp.message)
                    }
                } ?: run {
                    // response.body()가 null인 경우의 처리
                    Log.d("Code/FAILURE", "응답 본문이 비어 있습니다.")

                    //서버랑 연결이 안될 때(network오류)
                    resetPasswordView.onFailure("서버로부터 응답을 받지 못했습니다.")
                }
            }

            override fun onFailure(call: Call<ResetResponse>, t: Throwable) {
                Log.d("Password/FAILURE", t.message.toString())
            }
        })
        Log.d("Password", "HELLO")
    }
}