package com.example.a_write

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.TextView
import android.widget.EditText
import android.widget.ImageView
import android.widget.Toast
import com.example.a_write.api.MyPageService
import com.example.a_write.api.PasswordMatchListener
import com.google.gson.JsonObject

class DeleteAccountActivity : AppCompatActivity(), DeleteAccountDialog.OnBtnSelectedListener,
    PasswordMatchListener {
    private lateinit var withdrawBtn: TextView
    private lateinit var myPageService: MyPageService

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_delete_account)

        // 상단 이전 버튼
        val arrowBtn: ImageView = findViewById(R.id.previous_arrow_iv)
        arrowBtn.setOnClickListener {
            finish()
        }

        // 뒤로 가기 버튼
        val backBtn: TextView = findViewById(R.id.back_btn)
        backBtn.setOnClickListener {
            finish()
        }

        // 탈퇴하기 버튼
        withdrawBtn = findViewById(R.id.withdraw_btn)
        withdrawBtn.setOnClickListener {
            val passwordEditText: EditText = findViewById(R.id.current_password_input_etv)
            val enteredPassword = passwordEditText.text.toString()
            val pwData = JsonObject().apply {
                addProperty("password", enteredPassword)
            }

            myPageService = MyPageService(applicationContext)
            myPageService.passwordsMatch(this, pwData)
        }
    }

    override fun onDataLoaded(data: Boolean) {
        if(data) {
            showWithdrawDialog()
        } else {
            showToast("비밀번호가 일치하지 않습니다.")
        }
    }


    private fun showWithdrawDialog() {
        val deleteAccountDialog = DeleteAccountDialog(this, this)
        deleteAccountDialog.show()
    }

    override fun onBtnSelected() {
        // 로그인 화면으로 이동
        val loginIntent = Intent(this, LoginActivity::class.java)
        startActivity(loginIntent)
        finish()
    }

    private fun showToast(message: String) {
        Toast.makeText(this, message, Toast.LENGTH_SHORT).show()
    }
}
