package com.example.a_write

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.EditText
import android.widget.ImageView
import android.widget.TextView

class ProfileSettingActivity : AppCompatActivity(), ProfileChooseIconDialog.OnIconSelectedListener {
    private lateinit var profileImageView: ImageView
    private var originalProfileId: Int = 1

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_profile_setting)

        // 프로필 클릭 시 프로필 선택 다이얼로그 실행
        profileImageView = findViewById(R.id.setting_profile_iv)
        profileImageView.setOnClickListener {
            showChooseIconDialog()
        }

        // 프로필 이미지 띄우기
        profileImageView.setImageResource(getProfileImageResourceId(originalProfileId))

        // 닉네임 수정 값 관리
        val nicknameEditText: EditText = findViewById(R.id.setting_nickname_input_etv)
        val enteredNickname = nicknameEditText.text.toString()

        // 사용자 정보 수정 취소 (이전 화면으로 돌아가기)
        val editPreviousBtn: TextView = findViewById(R.id.edit_previous_btn)
        editPreviousBtn.setOnClickListener {
            finish()
        }

        // 사용자 정보 수정 완료
        val editCompleteBtn: TextView = findViewById(R.id.edit_complete_btn)
        editCompleteBtn.setOnClickListener {
            finish()
        }

        // 탈퇴하기
        val settingWithdrawTextView: TextView = findViewById(R.id.setting_withdraw_tv)
        settingWithdrawTextView.setOnClickListener {
            val intent = Intent(this, DeleteAccountActivity::class.java)
            startActivity(intent)
        }

    }

    private fun showChooseIconDialog() {
        val profileChooseIconDialog = ProfileChooseIconDialog(this, this, originalProfileId)
        profileChooseIconDialog.show()
    }

    override fun onIconSelected(selectedProfileValue: Int) {
        profileImageView.setImageResource(getProfileImageResourceId(selectedProfileValue))
    }
}

fun getProfileImageResourceId(profileId: Int): Int {
    return when (profileId) {
        1 -> R.drawable.profile_1
        2 -> R.drawable.profile_2
        3 -> R.drawable.profile_3
        4 -> R.drawable.profile_4
        else -> R.drawable.profile_1
    }
}