package com.example.a_write


import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import com.example.a_write.api.AuthService
import com.example.a_write.databinding.FragmentSignupBinding


class SignUpFragment : Fragment() ,SignUpView{
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

                    val authService = AuthService()
                    authService.setSignUpView(this)
                    authService.signUp(getUser())

                }
            }
            else{
                //message 발생(check해주세요)
            }
        }

        return binding.root
    }


    private fun signUp(): Boolean{
        val email = binding.signupEmailEt.text.toString()
        val password = binding.signupPasswordEt.text.toString()
        val nickname = binding.signupNicknameEt.text.toString()


        if (!(email.contains('@') && email.indexOf('@') != email.lastIndex)) {
            //@뒤에 들어가야함
            Toast.makeText(activity, "이메일 형식이 잘못되었습니다.", Toast.LENGTH_SHORT).show()
            return false
        }

        if (password.length !in 8..20) {
            //8~20자리
            Toast.makeText(activity, "비밀번호 형식이 잘못되었습니다.", Toast.LENGTH_SHORT).show()
            return false
        }

        if (nickname.length !in 2..10) {

            //2~10자리
            Toast.makeText(activity, "닉네임 형식이 잘못되었습니다.", Toast.LENGTH_SHORT).show()
            return false
        }
        return true
    }


    private fun getUser(): User {
        val email: String = binding.signupEmailEt.text.toString()
        val name: String = binding.signupNicknameEt.text.toString()
        val pwd: String = binding.signupPasswordEt.text.toString()

        return User(email, pwd, name)
    }

    override fun onSignUpSuccess() {
        Log.d("SIGNUP", "Success")

        //실사용 땐 고치기
        Toast.makeText(activity, "signup 성공", Toast.LENGTH_SHORT).show()

        (context as SignUpActivity).supportFragmentManager.beginTransaction()
            .replace(R.id.signup_frm, EmailFragment().apply {
                arguments = Bundle().apply {
                    putString("email", getUser().email)
                    putString("name", getUser().nickname)
                    putString("pwd",getUser().password)
                }
            }).commitAllowingStateLoss()
    }

    override fun onSignUpFailure(message: String) {
        Log.d("SIGNUP", "fail")
        Toast.makeText(activity, message, Toast.LENGTH_SHORT).show()
    }


}
