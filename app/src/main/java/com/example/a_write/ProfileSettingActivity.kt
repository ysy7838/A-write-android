package com.example.a_write

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.ImageView
import android.widget.TextView

class ProfileSettingActivity : AppCompatActivity(), ChooseIconDialog.OnIconSelectedListener {
    private lateinit var profileImageView: ImageView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_profile_setting)

        //프로필 클릭 시 프로필 선택 다이얼로그 실행
        profileImageView = findViewById(R.id.setting_profile_iv)
        profileImageView.setOnClickListener {
            showChooseIconDialog()
        }

        //사용자 정보 수정 취소 (이전 화면으로 돌아가기)
        val editPreviousBtn: TextView = findViewById(R.id.edit_previous_btn)
        editPreviousBtn.setOnClickListener {
            finish()
        }

        //사용자 정보 수정 완료
        val editCompleteBtn: TextView = findViewById(R.id.edit_complete_btn)
        editCompleteBtn.setOnClickListener {
            finish()
        }

        //탈퇴하기
        val settingWithdrawTextView: TextView = findViewById(R.id.setting_withdraw_tv)
        settingWithdrawTextView.setOnClickListener {
            val intent = Intent(this, DeleteAccountActivity::class.java)
            startActivity(intent)
        }

    }

    private fun showChooseIconDialog() {
        val chooseIconDialog = ChooseIconDialog(this, this)
        chooseIconDialog.show()
    }

    override fun onIconSelected(selectedIconResourceId: Int) {
        profileImageView.setImageResource(selectedIconResourceId)
    }
}
