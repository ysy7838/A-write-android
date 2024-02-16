package com.example.a_write.api

import android.util.Log
import com.example.a_write.CodeCheck
import com.example.a_write.CodeCheckView
import com.example.a_write.CodeSend
import com.example.a_write.CodeView
import retrofit2.Call
import retrofit2.Response

class CodeService {
    private lateinit var codeView: CodeView
    private lateinit var codeCheckView: CodeCheckView


    fun setCodeView(codeView: CodeView) {
        this.codeView = codeView
    }

    fun setCodeCheckView(checkView:CodeCheckView){
        this.codeCheckView = checkView
    }

    fun sendCode(code: CodeSend) {
        Log.d("Code/REQUEST", code.toString())

        val codeService = getRetrofit().create(CodeRetrofitInterface::class.java)
        codeService.sendCode(code).enqueue(object: retrofit2.Callback<CodeResponse>{

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



    fun codeCheck(code: CodeCheck) {
        Log.d("Code/REQUEST", code.toString())

        val codeService = getRetrofit().create(CodeRetrofitInterface::class.java)
        codeService.verificationCode(code).enqueue(object: retrofit2.Callback<CodeResponse>{

            override fun onResponse(call: Call<CodeResponse>, response: Response<CodeResponse>) {
                Log.d("Check/SUCCESS", response.toString())

                response.body()?.let { resp ->
                    when(resp.code){
                        "COMMON200" -> codeCheckView.onSuccess()
                        else -> codeCheckView.onFailure(resp.message)
                    }
                } ?: run {
                    // response.body()가 null인 경우의 처리
                    Log.d("Check/FAILURE", "응답 본문이 비어 있습니다.")

                    //서버랑 연결이 안될 때(network오류)
                    codeCheckView.onFailure("서버로부터 응답을 받지 못했습니다.")
                }
            }

            override fun onFailure(call: Call<CodeResponse>, t: Throwable) {
                Log.d("Check/FAILURE", t.message.toString())
            }
        })
        Log.d("Check", "HELLO")
    }

}