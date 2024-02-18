package com.example.a_write

import android.content.Context
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.EditText
import android.widget.ImageView
import android.widget.TextView
import com.example.a_write.api.MyPageService
import com.example.a_write.api.MyPageUserInfoListener
import com.example.a_write.api.UserInfo
import com.google.gson.JsonObject
import kotlinx.coroutines.*

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

            GlobalScope.launch {
                val patchNicknameDeferred = async { myPageService.patchNickname(nicknameData) }
                val patchProfileDeferred = async { myPageService.patchProfile(profileData) }

                patchNicknameDeferred.await()
                patchProfileDeferred.await()

                delay(100)
                finish()
            }

        }

        // 비밀번호 재설정하기 (ResetActivity 실행)
        val settingPasswordResetTextView: TextView = findViewById(R.id.setting_password_reset_tv)
        settingPasswordResetTextView.setOnClickListener {
            val resetIntent = Intent(this, ResetActivity::class.java)
            startActivity(resetIntent)
        }

        // 로그아웃 (LoginActivity 실행)
        val settingLogoutTextView: TextView = findViewById(R.id.setting_logout_tv)
        settingLogoutTextView.setOnClickListener {
            logout(this)
        }

        // 탈퇴하기 (DeleteAccountActivity 실행)
        val settingWithdrawTextView: TextView = findViewById(R.id.setting_withdraw_tv)
        settingWithdrawTextView.setOnClickListener {
            val intent = Intent(this, DeleteAccountActivity::class.java)
            startActivity(intent)
        }

    }

    private fun logout(context: Context) {
        val spf = context.getSharedPreferences("auth2", Context.MODE_PRIVATE)
        val editor = spf.edit()
        editor.clear()
        editor.apply()

        val intent = Intent(context, LoginActivity::class.java)
        intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
        context.startActivity(intent)
    }

    override fun onUserDataLoaded(data: UserInfo) {
        originalProfileId = data.profileImg
        selectedProfileId = data.profileImg
        Log.d("API originalProfileId", originalProfileId.toString())

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