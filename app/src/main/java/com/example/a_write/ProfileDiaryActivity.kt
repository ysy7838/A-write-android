package com.example.a_write

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import android.util.Log
import android.widget.ImageView

class ProfileDiaryActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_profile_diary)

        val selectedDate = intent.getStringExtra("selectedDate")

        Log.d("DiaryActivity", "Selected Date: $selectedDate")


        //이전 화면으로 돌아가기
        val previousBtn: ImageView = findViewById(R.id.previous_arrow_iv)
        previousBtn.setOnClickListener {
            finish()
        }
    }
}