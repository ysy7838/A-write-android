package com.example.a_write

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import com.example.a_write.databinding.FragmentEmailBinding

class EmailFragment :Fragment(), SignUpView{
    lateinit var binding : FragmentEmailBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        binding = FragmentEmailBinding.inflate(inflater, container, false)


        binding.emailSendButtonTv.setOnClickListener{


            binding.emailExplain3Tv.visibility=View.VISIBLE
            binding.emailExplain4Tv.visibility=View.VISIBLE

            binding.emailSendButtonTv.setBackgroundColor(ContextCompat.getColor(requireActivity(), R.color.grayA))

            binding.codeNextBt.visibility=View.VISIBLE
            //send email
//            val authService = AuthService()
//            authService.setSignUpView(this)
//
//            authService.signUp(getUser())


        }

        binding.codeNextBt.setOnClickListener{
            (context as SignUpActivity).supportFragmentManager.beginTransaction()
                .replace(R.id.signup_frm, CodeFragment())
                .commit()
        }

        binding.emailLoginButtonTv.setOnClickListener{
            //goto login process
            val intent = Intent(activity,LoginActivity::class.java)
            intent.flags = Intent.FLAG_ACTIVITY_CLEAR_TOP
            startActivity(intent)
        }

        return binding.root
    }

    override fun onSignUpSuccess() {
        Log.d("SIGNUP", "Success")
        Toast.makeText(activity, "signup 성공", Toast.LENGTH_SHORT).show()
    }



    override fun onSignUpFailure(message: String) {
        Log.d("SIGNUP", "fail")
        Toast.makeText(activity, message, Toast.LENGTH_SHORT).show()
    }


    private fun getUser(): User {
        val email: String = binding.emailNameEt.toString()
        val pwd = arguments?.getString("pwd") ?:""
        val name = arguments?.getString("name") ?:""

        return User(email, pwd, name)
    }

}