package com.example.a_write

import android.app.Dialog
import android.content.Context
import android.os.Bundle
import android.util.Log
import android.widget.ImageView
import android.widget.TextView

class ProfileChooseIconDialog(
    context: Context,
    private val listener: OnIconSelectedListener,
    private val originalProfileResourceId: Int
) : Dialog(context) {

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

        icon1ImageView.setOnClickListener { onSelectIconClick(1) }
        icon2ImageView.setOnClickListener { onSelectIconClick(2) }
        icon3ImageView.setOnClickListener { onSelectIconClick(3) }
        icon4ImageView.setOnClickListener { onSelectIconClick(4) }

        // 프로필 변경 취소 버튼
        val cancelBtn: TextView = findViewById(R.id.profile_cancel_btn)
        cancelBtn.setOnClickListener {
            onSelectIconClick(originalProfileResourceId)
            dismiss()
        }

        // 프로필 선택 완료 버튼
        val selectBtn: TextView = findViewById(R.id.profile_select_btn)
        selectBtn.setOnClickListener {
            dismiss()
        }

        // 다이얼로그 바깥 클릭 방지
        setCanceledOnTouchOutside(false)
    }

    private fun onSelectIconClick(selectedProfileValue: Int) {
        listener.onIconSelected(selectedProfileValue)
        Log.d("API selectedProfile", selectedProfileValue.toString())
    }

    interface OnIconSelectedListener {
        fun onIconSelected(selectedProfileValue: Int)
    }
}

