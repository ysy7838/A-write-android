package com.example.a_write

import android.app.Dialog
import android.content.Context
import android.graphics.Point
import android.os.Bundle
import android.view.Gravity
import android.widget.TextView


class DeleteAccountDialog(context: Context, private val listener: OnBtnSelectedListener) : Dialog(context) {

    private var isConfirmationMode = false

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.dialog_delete_account)

        // Dialog의 배경을 투명하게 설정
        window?.setBackgroundDrawableResource(android.R.color.transparent)

        val display = window?.windowManager?.defaultDisplay
        val size = Point()
        display?.getSize(size)

        val width = size.x
        val params = window?.attributes
        params?.width = width
        window?.attributes = params

        params?.gravity = Gravity.TOP
        window?.attributes = params

        // 예/확인 버튼 선택
        findViewById<TextView>(R.id.withdraw_yes_btn).setOnClickListener {
            if (isConfirmationMode) {
                handleConfirmationButtonClick()
            } else {
                showConfirmationText()
            }
        }

        // 아니오 버튼 선택
        findViewById<TextView>(R.id.withdraw_no_btn).setOnClickListener {
            dismiss()
        }
    }

    private fun showConfirmationText() {
        isConfirmationMode = true
        findViewById<TextView>(R.id.delete_confirm_tv).text = context.getString(R.string.notification)
        findViewById<TextView>(R.id.delete_confirm_description_tv).text = context.getString(R.string.delete_account_complete_text)
        findViewById<TextView>(R.id.withdraw_yes_btn).text = context.getString(R.string.confirmation)
        val widthInPixels = context.resources.getDimensionPixelSize(R.dimen.width_64dp)
        findViewById<TextView>(R.id.withdraw_yes_btn).layoutParams.width = widthInPixels
        findViewById<TextView>(R.id.withdraw_no_btn).visibility = TextView.GONE
    }

    private fun handleConfirmationButtonClick() {
        listener.onBtnSelected(true)
        dismiss()

        //로그인 화면으로 이동
    }

    interface OnBtnSelectedListener {
        fun onBtnSelected(clickedBtn: Boolean)
    }
}


