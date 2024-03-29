package com.example.a_write

import android.content.Context
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import com.example.a_write.api.ResetService
import com.example.a_write.databinding.FragmentResetPasswordBinding


class ResetPasswordFragment : Fragment() , ResetPasswordView{
    lateinit var binding : FragmentResetPasswordBinding

    var flag = false
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?



    ): View? {
        binding = FragmentResetPasswordBinding.inflate(inflater, container, false)


        val email = context?.getSharedPreferences("AppPreferences", Context.MODE_PRIVATE)?.getString("emailKey", null)
        Log.d("Email", email.toString())

        binding.resetEmailTv.text = email.toString()

        val token = arguments?.getString("token") ?: return binding.root



        binding.resetBackIv.setOnClickListener{
            (context as ResetActivity).supportFragmentManager.beginTransaction()
                .replace(R.id.reset_frm, ResetSendFragment())
                .commitAllowingStateLoss()
        }


        val textWatcher = object : TextWatcher {
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {}

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {}

            override fun afterTextChanged(s: Editable?) {
                // 두 EditText의 텍스트를 비교하여 flag 값을 업데이트합니다.
                flag = binding.resetNewpwdEt.text.toString() == binding.resetPwdEt.text.toString() &&
                        binding.resetNewpwdEt.text.toString().isNotEmpty()
                changeColor(flag)
            }
        }

        binding.resetNewpwdEt.addTextChangedListener(textWatcher)
        binding.resetPwdEt.addTextChangedListener(textWatcher)

        binding.resetCompleteBt.setOnClickListener {
            if(flag){
                //비밀번호 재설정 완료 api
                val resetService = ResetService()
                resetService.setPasswordView(this)

                resetService.passwordCheck(Password(binding.resetNewpwdEt.text.toString(), binding.resetPwdEt.text.toString()), token)


            }
            else{
                Toast.makeText(activity, "두 비밀번호가 다릅니다.", Toast.LENGTH_SHORT).show()
            }
        }

        return binding.root
    }



    private fun changeColor(flag:Boolean){
        if(flag){
            binding.resetCompleteBt.setBackgroundColor(ContextCompat.getColor(requireActivity(), R.color.red))
            binding.resetCompleteBt.setTextColor(ContextCompat.getColor(requireActivity(), R.color.white))
        }
        else{
            binding.resetCompleteBt.setBackgroundColor(ContextCompat.getColor(requireActivity(), R.color.gray))
            binding.resetCompleteBt.setTextColor(ContextCompat.getColor(requireActivity(), R.color.black))

        }
    }



    override fun onSuccess() {
        //팝업 발생
        val popupFragment = PopupFragment(requireContext())
        popupFragment.show()
    }

    override fun onFailure(message: String) {
        Log.d("PasswordCheck", "fail")
        Toast.makeText(activity, message, Toast.LENGTH_SHORT).show()
    }

}
