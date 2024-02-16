package com.example.a_write.api

import android.util.Log
import com.example.a_write.CodeView
import retrofit2.Call
import retrofit2.Response

class CodeService {
    private lateinit var codeView: CodeView

    fun setCodeView(codeView: CodeView) {
        this.codeView = codeView
    }

    fun sendCode(email: String) {

        val codeService = getRetrofit().create(CodeRetrofitInterface::class.java)
        codeService.sendCode(email).enqueue(object: retrofit2.Callback<CodeResponse>{

            override fun onResponse(call: Call<CodeResponse>, response: Response<CodeResponse>) {
                Log.d("Code/SUCCESS", response.toString())

                response.body()?.let { resp ->
                    when(resp.code){
                        "COMMON200" -> codeView.onEmailCodeSuccess()
                        else -> codeView.onEmailCodeFailure(resp.message)
                    }
                } ?: run {
                    // response.body()가 null인 경우의 처리
                    Log.d("Code/FAILURE", "응답 본문이 비어 있습니다.")

                    //서버랑 연결이 안될 때(network오류)
                    codeView.onEmailCodeFailure("서버로부터 응답을 받지 못했습니다.")
                }

            }

            override fun onFailure(call: Call<CodeResponse>, t: Throwable) {
                Log.d("Code/FAILURE", t.message.toString())

            }


        })
        Log.d("Code", "HELLO")
    }
}