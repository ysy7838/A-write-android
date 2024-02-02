package com.example.a_write


import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import com.example.a_write.databinding.FragmentSignupBinding
import com.google.gson.Gson


class SignUpFragment : Fragment() {
    lateinit var binding : FragmentSignupBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentSignupBinding.inflate(inflater, container, false)

        binding.signupCloseIv.setOnClickListener{
            val intent = Intent(activity,LoginActivity::class.java)
            intent.flags = Intent.FLAG_ACTIVITY_CLEAR_TOP
            startActivity(intent)
        }

        binding.signupButtonTv.setOnClickListener{
            if(binding.myCheckBox1Cb.isChecked && binding.myCheckBox2Cb.isChecked && binding.myCheckBox3Cb.isChecked ){

                if(signUp()){
                    val email: String = binding.signupEmailEt.text.toString()
                    val name: String = binding.signupNicknameEt.text.toString()
                    val pwd: String = binding.signupPasswordEt.text.toString()

                    (context as SignUpActivity).supportFragmentManager.beginTransaction()
                        .replace(R.id.signup_frm, EmailFragment().apply {
                            arguments = Bundle().apply {
                                putString("email", email)
                                putString("name", name)
                                putString("pwd",pwd )
                            }
                        }).commitAllowingStateLoss()
                }
            }
            else{
                //message 발생(check필요)
            }
        }

        return binding.root
    }


    private fun signUp(): Boolean{
        if (binding.signupEmailEt.text.toString().isEmpty()) {
            Toast.makeText(activity, "이메일 형식이 잘못되었습니다.", Toast.LENGTH_SHORT).show()
            return false
        }

        if (binding.signupPasswordEt.text.toString().isEmpty()) {
            Toast.makeText(activity, "비밀번호 형식이 잘못되었습니다.", Toast.LENGTH_SHORT).show()
            return false
        }

        if (binding.signupNicknameEt.text.toString().isEmpty()) {
            Toast.makeText(activity, "닉네임 형식이 잘못되었습니다.", Toast.LENGTH_SHORT).show()
            return false
        }
        return true
    }




}
