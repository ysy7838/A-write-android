package com.example.a_write

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.EditText
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.a_write.api.MyPageDiary
import com.example.a_write.api.MyPageService
import com.example.a_write.api.MyPageUserInfoListener
import com.example.a_write.api.UserInfo
import com.google.gson.JsonObject

class ProfileSettingActivity : AppCompatActivity(), ProfileChooseIconDialog.OnIconSelectedListener,
    MyPageUserInfoListener {
    private lateinit var profileImageView: ImageView
    private var originalProfileId: Int = 1
    private var selectedProfileId: Int = 1
    private lateinit var myPageService: MyPageService

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_profile_setting)

        myPageService = MyPageService(applicationContext)
        myPageService.getUserInfo(this)

        // 프로필 클릭 시 프로필 선택 다이얼로그 실행
        profileImageView = findViewById(R.id.setting_profile_iv)
        profileImageView.setOnClickListener {
            showChooseIconDialog()
        }

        // 사용자 정보 수정 취소 (이전 화면으로 돌아가기)
        val editPreviousBtn: TextView = findViewById(R.id.edit_previous_btn)
        editPreviousBtn.setOnClickListener {
            finish()
        }


        // 사용자 정보 수정 완료
        val editCompleteBtn: TextView = findViewById(R.id.edit_complete_btn)
        editCompleteBtn.setOnClickListener {

            // 닉네임 수정 값 관리
            val nicknameEditText: EditText = findViewById(R.id.setting_nickname_input_etv)
            val enteredNickname = nicknameEditText.text.toString()

            val nicknameData = JsonObject().apply {
                addProperty("nickname", enteredNickname)
            }

            val profileData = JsonObject().apply {
                addProperty("profileImg", selectedProfileId)
            }

            if(enteredNickname != null) {
                myPageService.patchNickname(nicknameData)
            }

            if(originalProfileId != selectedProfileId) {
                myPageService.patchProfile(profileData)
            }

            finish()
        }

        // 비밀번호 재설정하기 (ResetActivity 실행)
        val settingPasswordResetTextView: TextView = findViewById(R.id.setting_password_reset_tv)
        settingPasswordResetTextView.setOnClickListener {
            val resetIntent = Intent(this, ResetActivity::class.java)
            startActivity(resetIntent)
        }

        // 탈퇴하기 (DeleteAccountActivity 실행)
        val settingWithdrawTextView: TextView = findViewById(R.id.setting_withdraw_tv)
        settingWithdrawTextView.setOnClickListener {
            val intent = Intent(this, DeleteAccountActivity::class.java)
            startActivity(intent)
        }

    }

    override fun onUserDataLoaded(data: UserInfo) {
        originalProfileId = data.profileImg
        Log.d("API oP", originalProfileId.toString())

        // 프로필 이미지 띄우기
        profileImageView.setImageResource(getProfileImageResourceId(originalProfileId))

        val nicknameTextView: TextView = findViewById(R.id.setting_nickname_input_etv)
        val emailTextView: TextView = findViewById(R.id.setting_email_input_tv)
        nicknameTextView.text = data.nickname
        emailTextView.text = data.email

    }

    private fun showChooseIconDialog() {
        val profileChooseIconDialog = ProfileChooseIconDialog(this, this, originalProfileId)
        profileChooseIconDialog.show()
    }

    override fun onIconSelected(selectedProfileValue: Int) {
        profileImageView.setImageResource(getProfileImageResourceId(selectedProfileValue))
        selectedProfileId = selectedProfileValue
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