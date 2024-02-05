package com.example.a_write

import android.app.Dialog
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.graphics.drawable.Drawable
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import android.util.Log
import android.view.View
import android.view.Window
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.ScrollView
import android.widget.TextView
import androidx.core.content.ContextCompat
import com.squareup.picasso.Picasso

class ProfileDiaryActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_profile_diary)

        // 임시 데이터 바인딩
        val titleTextView: TextView = findViewById(R.id.profile_diary_title_tv)
        val contentTextView: TextView = findViewById(R.id.profile_diary_content_tv)
        val diaryImageView: ImageView = findViewById(R.id.profile_diary_img_iv)

        titleTextView.text = intent.getStringExtra("diary_title")
        contentTextView.text = intent.getStringExtra("diary_content")
        Picasso.get().load(intent.getStringExtra("diary_img")).into(diaryImageView)

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

        // 이미지 전체보기 다이얼로그
        val profileDiaryImageView = findViewById<ImageView>(R.id.profile_diary_img_iv)
        profileDiaryImageView.setOnClickListener {
            showImageDialog(profileDiaryImageView.drawable)
        }
    }

    private fun showImageDialog(drawable: Drawable) {
        val dialog = Dialog(this)
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE)
        dialog.setContentView(R.layout.dialog_diary_detail_img)

        val imageView = dialog.findViewById<ImageView>(R.id.diary_detail_dialog_iv)
        imageView.setImageDrawable(drawable)
        imageView.setOnClickListener { dialog.dismiss() }
        dialog.window!!.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
        dialog.show()
    }
}