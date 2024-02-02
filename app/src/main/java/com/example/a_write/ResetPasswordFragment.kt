package com.example.a_write

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import com.example.a_write.databinding.FragmentResetPasswordBinding


class ResetPasswordFragment : Fragment() {
    lateinit var binding : FragmentResetPasswordBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentResetPasswordBinding.inflate(inflater, container, false)


        binding.resetBackIv.setOnClickListener{
            (context as ResetActivity).supportFragmentManager.beginTransaction()
                .replace(R.id.reset_frm, ResetSendFragment())
        }

        binding.resetCompleteBt.setOnClickListener {
            //팝업 발생
            val popupFragment = PopupFragment()
            val bundle = Bundle().apply {
                putString(
                    "text",
                    "새 비밀번호가 설정되었습니다."
                )
            }
            popupFragment.arguments = bundle
            popupFragment.show(childFragmentManager, "popupFragment")
        }

        return binding.root
    }

}
