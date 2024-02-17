package com.example.a_write

import android.net.Uri
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.example.a_write.databinding.ActivityResetBinding

class ResetActivity : AppCompatActivity() {

    lateinit var binding: ActivityResetBinding


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityResetBinding.inflate(layoutInflater)
        setContentView(binding.root)

        // 인텐트에서 데이터 가져오기
        val data: Uri? = intent?.data

        // 링크를 통한 접속인지 확인
        val token = data?.getQueryParameter("token")

        if (token != null) {
            // 토큰이 있으면, 해당 토큰을 사용해 프래그먼트로 이동
            navigateToFragmentWithToken(token)
        } else {
            // 토큰이 없으면, 일반적인 앱 시작 로직 실행
            start()
        }
    }


    private fun start(){
        supportFragmentManager.beginTransaction()
            .replace(R.id.reset_frm,ResetSendFragment())
            .commitAllowingStateLoss()
    }

    private fun navigateToFragmentWithToken(token: String) {
        val fragment = ResetPasswordFragment().apply {
            arguments = Bundle().apply {
                putString("token", token)
            }
        }

        supportFragmentManager.beginTransaction()
            .replace(R.id.reset_frm, fragment)
            .commit()
    }
}
