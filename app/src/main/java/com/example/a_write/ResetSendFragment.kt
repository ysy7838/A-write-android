package com.example.a_write



import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import com.example.a_write.databinding.FragmentResetSendBinding


class ResetSendFragment : Fragment() {
    lateinit var binding : FragmentResetSendBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentResetSendBinding.inflate(inflater, container, false)


        binding.resetCloseIv.setOnClickListener {
            val intent = Intent(activity, LoginActivity::class.java)
            intent.flags = Intent.FLAG_ACTIVITY_CLEAR_TOP
            startActivity(intent)
        }


        binding.resetEmailSendBt.setOnClickListener {
            binding.resetEmailSendBt.setBackgroundColor(ContextCompat.getColor(requireActivity(), R.color.grayA))

            binding.resetExplain2Tv.visibility = View.VISIBLE
            binding.resetExplain2Iv.visibility = View.VISIBLE

            //이메일 인증 보내기

        }

        //api 완성 시 삭제
        binding.codeNextBt.setOnClickListener{
            (context as ResetActivity).supportFragmentManager.beginTransaction()
                .replace(R.id.reset_frm, ResetPasswordFragment())
                .commitAllowingStateLoss()
        }

        return binding.root
    }


}
