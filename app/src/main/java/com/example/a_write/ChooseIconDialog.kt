package com.example.a_write

import android.app.Dialog
import android.content.Context
import android.os.Bundle
import android.widget.ImageView

class ChooseIconDialog(context: Context, private val listener: OnIconSelectedListener) : Dialog(context) {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.dialog_choose_icon)

        // Dialog의 배경을 투명하게 설정
        window?.setBackgroundDrawableResource(android.R.color.transparent)

        // 아이콘 이미지뷰 초기화 및 클릭 이벤트 설정
        val icon1ImageView: ImageView = findViewById(R.id.profile_1_iv)
        val icon2ImageView: ImageView = findViewById(R.id.profile_2_iv)
        val icon3ImageView: ImageView = findViewById(R.id.profile_3_iv)
        val icon4ImageView: ImageView = findViewById(R.id.profile_4_iv)

        icon1ImageView.setOnClickListener { onSelectIconClick(R.drawable.profile_1) }
        icon2ImageView.setOnClickListener { onSelectIconClick(R.drawable.profile_2) }
        icon3ImageView.setOnClickListener { onSelectIconClick(R.drawable.profile_3) }
        icon4ImageView.setOnClickListener { onSelectIconClick(R.drawable.profile_4) }
    }

    private fun onSelectIconClick(selectedIconResourceId: Int) {
        listener.onIconSelected(selectedIconResourceId)
        dismiss() // 다이얼로그 닫기
    }

    interface OnIconSelectedListener {
        fun onIconSelected(selectedIconResourceId: Int)
    }
}

