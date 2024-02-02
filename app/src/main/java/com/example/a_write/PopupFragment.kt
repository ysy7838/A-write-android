package com.example.a_write

import android.app.Dialog
import android.content.Intent
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.os.Bundle
import android.view.Gravity
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.Window
import androidx.fragment.app.DialogFragment
import com.example.a_write.databinding.FragmentPopupBinding

class PopupFragment : DialogFragment() {
    lateinit var binding : FragmentPopupBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setStyle(STYLE_NORMAL, R.style.DialogStyle)
    }

    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        val dialog = super.onCreateDialog(savedInstanceState)
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE)
        return dialog
    }


    override fun onStart() {
        super.onStart()
        val window = dialog?.window
        window?.setLayout(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT)
        window?.setGravity(Gravity.TOP) //
    }


    override fun onCreateView(

        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentPopupBinding.inflate(inflater, container, false)



        val text = arguments?.getString("text")
        binding.popupTextTv.text = text


        binding.popupCloseIv.setOnClickListener{
            dismiss()
        }

        //추가요함
        binding.popupConfirm.setOnClickListener {
            if (this.parentFragment is ResetSendFragment) {
                (context as ResetActivity).supportFragmentManager.beginTransaction()
                    .replace(R.id.reset_frm, ResetPasswordFragment())
                    .commit()
            }
            else if (this.parentFragment is ResetPasswordFragment) {
                val intent = Intent(activity, LoginActivity::class.java)
                intent.flags = Intent.FLAG_ACTIVITY_CLEAR_TOP
                startActivity(intent)
            }
        }


        return binding.root
    }

}



