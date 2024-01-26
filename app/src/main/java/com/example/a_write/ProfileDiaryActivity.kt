package com.example.a_write

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import android.util.Log
import android.view.View
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.ScrollView
import androidx.core.content.ContextCompat

class ProfileDiaryActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_profile_diary)

        // 선택한 날짜 확인
        val selectedDate = intent.getStringExtra("selectedDate")
        val selectedYear = intent.getIntExtra("selectedYear", 0)
        val selectedMonth = intent.getIntExtra("selectedMonth", 0)

        Log.d("ProfileDiaryActivity", "Selected Date: $selectedYear $selectedMonth $selectedDate")

        // 이전 화면으로 돌아가기
        val previousBtn: ImageView = findViewById(R.id.previous_arrow_iv)
        previousBtn.setOnClickListener {
            finish()
        }

        // 삭제하기 버튼 띄우기
        val menuImageView: ImageView = findViewById(R.id.profile_diary_menu_iv)
        val deleteButtonLayout: LinearLayout = findViewById(R.id.profile_diary_del_btn)

        menuImageView.setOnClickListener {
            deleteButtonLayout.visibility =
                if (deleteButtonLayout.visibility == View.VISIBLE) View.INVISIBLE else View.VISIBLE
        }

        // 일기 삭제하기 (삭제 API 호출 후 이전 화면으로 이동)
        deleteButtonLayout.setOnClickListener {
            finish()
        }

        // 일기 테마 적용하기
        val diaryTheme: Int = intent.getIntExtra("diary_theme", 0)
        val scrollView = findViewById<ScrollView>(R.id.profile_diary_sv)

        when (diaryTheme) {
            1 -> scrollView.setBackgroundColor(ContextCompat.getColor(this, R.color.theme_blue))
            2 -> scrollView.setBackgroundColor(ContextCompat.getColor(this, R.color.theme_green))
            3 -> scrollView.setBackgroundColor(ContextCompat.getColor(this, R.color.theme_yellow))
            4 -> scrollView.setBackgroundColor(ContextCompat.getColor(this, R.color.theme_red))
            else -> {
                scrollView.setBackgroundColor(ContextCompat.getColor(this, R.color.your_app_background_color))
            }
        }
    }
}