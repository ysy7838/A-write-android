package com.example.a_write

import android.app.Dialog
import android.content.Context
import android.content.Intent
import android.graphics.Point
import android.os.Bundle
import android.view.Gravity
import android.view.LayoutInflater
import com.example.a_write.databinding.FragmentPopupBinding

class PopupFragment(context: Context): Dialog(context) {

    lateinit var binding : FragmentPopupBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = FragmentPopupBinding.inflate(LayoutInflater.from(context))
        setContentView(binding.root)

        // Dialog의 배경을 투명하게 설정
        window?.setBackgroundDrawableResource(android.R.color.transparent)

        val display = window?.windowManager?.defaultDisplay
        val size = Point()
        display?.getSize(size)

        val width = size.x
        val params = window?.attributes
        params?.width = width
        params?.height = dpToPx(228, context) // 높이를 228dp로 설정

        window?.attributes = params

        params?.gravity = Gravity.TOP
        window?.attributes = params

        // 확인 버튼 선택
        binding.popupYesBtn.setOnClickListener {
            val intent = Intent(context, LoginActivity::class.java)
            intent.flags = Intent.FLAG_ACTIVITY_CLEAR_TOP
            context.startActivity(intent)
            dismiss()
        }

    }
    private fun dpToPx(dp: Int, context: Context): Int {
        return (dp * context.resources.displayMetrics.density).toInt()
    }



}



