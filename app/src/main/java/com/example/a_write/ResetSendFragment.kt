package com.example.a_write



import android.content.Context
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import com.example.a_write.api.ResetService
import com.example.a_write.databinding.FragmentResetSendBinding


class ResetSendFragment : Fragment(), ResetEmailView {
    lateinit var binding : FragmentResetSendBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentResetSendBinding.inflate(inflater, container, false)

        binding.resetCloseIv.setOnClickListener {
            activity?.finish()
        }

        binding.resetEmailSendBt.setOnClickListener {
            binding.resetEmailSendBt.setBackgroundColor(ContextCompat.getColor(requireActivity(), R.color.grayA))

            binding.resetExplain2Tv.visibility = View.VISIBLE
            val email = binding.resetEmailEt.text.toString()


            //이메일 인증 보내기
            val resetService = ResetService()
            resetService.setEmailView(this)

            resetService.sendEmail(EmailSend(email))

        }

        return binding.root
    }

    override fun onSuccess(email:String) {
        Toast.makeText(activity, "메일 발송 성공", Toast.LENGTH_SHORT).show()
        context?.getSharedPreferences("AppPreferences", Context.MODE_PRIVATE)?.edit()?.apply {
            putString("emailKey", email)
            apply()
        }
    }

    override fun onFailure(message: String) {
        Log.d("EmailCheck", "fail")
        Toast.makeText(activity, message, Toast.LENGTH_SHORT).show()
    }


}
